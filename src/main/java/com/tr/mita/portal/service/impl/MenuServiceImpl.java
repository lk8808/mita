package com.tr.mita.portal.service.impl;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.Rtsts;
import com.tr.mita.comm.entity.UserObject;
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
	public List<Menu> queryMenusByAppid(String appid) {
		return menuDao.queryMenusByAppid(appid);
	}

	@Override
	public List<Menu> queryMenusByParentid(String parentid) {
		return menuDao.queryMenusByParentid(parentid);
	}

	@Override
	public List<Map<String, Object>> getMenuTreeByAppid(String appid) {
		return getMenuTree(null, appid);
	}

	@Override
	public List<Map<String, Object>> getAppMenuTree() {
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
			node.put("key", "APP" + app.getId());
			node.put("children", getMenuTree(null, String.valueOf(app.getId())));
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	public List<Map<String, Object>> getAuthAppMenuTree() {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		//获取所有应用
		List<Application> apps = applicationDao.queryAllList();
		for (Application app : apps) {
			List<Map<String, Object>> menus = getAuthMenuTree(null, String.valueOf(app.getId()));
			if (menus == null || menus.size() < 1) {
				continue;
			}
			//创建节点
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("id", app.getId());
			node.put("type", "APP");
			node.put("name", app.getAppname());
			node.put("code", app.getAppcode());
			node.put("url", app.getUrl());
			node.put("icon", app.getIcon());
			node.put("sortno", app.getSortno());
			node.put("key", "APP" + app.getId());
			node.put("children", menus);
			nodes.add(node);
		}
		return nodes;
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
			node.put("key", "MENU" + menu.getId());
			node.put("bizdata", menu);
			List<Map<String, Object>> children = getMenuTree(String.valueOf(menu.getId()), null);
			if (children != null && children.size() > 0) {
				node.put("children", children);
			}
			nodes.add(node);
		}
		return nodes;
	}

	private List<Map<String, Object>> getAuthMenuTree(String parentid, String appid) {
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<String, Object>();
		List<Menu> menus = null;
		String token = request.getHeader("Token");
		UserObject userObject = (UserObject)redisUtil.get(token);
		if (parentid != null && !"".equals(parentid)) {
			params.put("userid", userObject.getUser().getId());
			params.put("parentid", parentid);
			menus = menuDao.queryAuthMenusByParentid(params);
		} else if (appid != null && !"".equals(appid)) {
			params.put("userid", userObject.getUser().getId());
			params.put("appid", appid);
			menus = menuDao.queryAuthMenusByAppid(params);
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
			node.put("key", "MENU" + menu.getId());
			node.put("bizdata", menu);
			List<Map<String, Object>> children = getAuthMenuTree(String.valueOf(menu.getId()), null);
			if (children != null && children.size() > 0) {
				node.put("children", children);
			}
			nodes.add(node);
		}
		return nodes;
	}

	@Override
	@Transactional
	public Integer save(Menu menu) {
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
		UserObject userObject = (UserObject)redisUtil.get(token);
		if (menu.getId() != null && menu.getId() > 0) {
			menu.setModifytime(new Date());
			menu.setModifier(userObject.getEmployee().getEmpname());
			return menuDao.update(menu);
		} else {
			menu.setDelflag("0");
			menu.setCreatetime(new Date());
			menu.setCreator(userObject.getEmployee().getEmpname());
			return menuDao.insert(menu);
		}
	}

	@Override
	@Transactional
	public Integer del(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			return menuDao.deleteBatch(idArr);
		}
		return 0;
	}

}
