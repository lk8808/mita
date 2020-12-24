package com.tr.mita.portal.service.impl;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import com.tr.mita.portal.dao.ApplicationDao;
import com.tr.mita.portal.dao.MenuDao;
import com.tr.mita.portal.model.Application;
import com.tr.mita.portal.model.Menu;
import com.tr.mita.portal.service.IMenuService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	public Menu get(Integer id) {
		return menuDao.get(id);
	}
	
	@Override
	public List<Menu> queryAllList() {
		return menuDao.queryAllList();
	}

	@Override
	public RespData queryMenusByAppid(String appid) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", menuDao.queryMenusByAppid(appid));
		return respData;
	}

	@Override
	public RespData queryMenusByParentid(String parentid) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", menuDao.queryMenusByParentid(parentid));
		return respData;
	}

	@Override
	public RespData getMenuTreeByAppid(String appid) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", getMenuTree(null, appid));
		return respData;
	}

	@Override
	public RespData getAppMenuTree() {
		RespData respData = new RespData();
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		//获取所有应用
		List<Application> apps = applicationDao.queryAllList();
		for (Application app : apps) {
			//创建节点
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", app.getId());
			node.put("type", "APP");
			node.put("name", app.getAppname());
			node.put("code", app.getAppcode());
			node.put("url", app.getUrl());
			node.put("icon", app.getIcon());
			node.put("sortno", app.getSortno());
			node.put("children", getMenuTree(null, String.valueOf(app.getId())));
			nodes.add(node);
		}
		respData.setRtdata("bizdatas", nodes);
		return respData;
	}


	/**
	 * 通过appid获取菜单树
	 * @param parentid
	 * @param appid
	 * @return
	 */
	private List<Map<String, Object>> getMenuTree(String parentid, String appid) {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		List<Menu> menus = null;
		if (parentid != null && !"".equals(parentid)) {
			menus = menuDao.queryMenusByParentid(parentid);
		} else if (appid != null && !"".equals(appid)) {
			menus = menuDao.queryMenusByAppid(appid);
		}
		if (menus == null) {
			return null;
		}
		for (Menu menu : menus) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", menu.getId());
			node.put("type", "MENU");
			node.put("name", menu.getMenuname());
			node.put("code", menu.getMenuno());
			node.put("url", menu.getMenuurl());
			node.put("sortno", menu.getSortno());
			node.put("bizdata", menu);
			List<Map<String, Object>> children = getMenuTree(String.valueOf(menu.getId()), null);
			if (children != null && children.size() > 0) {
				node.put("children", children);
			}
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	@Transactional
	public RespData save(Menu menu) {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		if (menu.getParentid() != null && menu.getParentid()>0) {
			Menu parent = get(menu.getParentid());
			if (parent != null) {
				int level = parent.getMenulevel();
				menu.setMenulevel(level + 1);
			}
		} else {
			menu.setMenulevel(1);
		}
		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			if (menu.getId() != null && menu.getId() > 0) {
				menu.setModifytime(new Date());
				menu.setModifier(userObject.getEmployee().getEmpname());
				menuDao.update(menu);
			} else {
				menu.setDelflag("0");
				menu.setCreatetime(new Date());
				menu.setCreator(userObject.getEmployee().getEmpname());
				menuDao.insert(menu);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	@Override
	@Transactional
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				menuDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}
	
	public List<Menu> queryAuthHomeMenus(){
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		if (token != null) {
			UserObject userObject = (UserObject)redisUtil.get(token);
			return menuDao.queryAuthHomeMenus(userObject.getUser().getId().toString());
		}
		return null;
	};
}
