package com.tr.ibs.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;
import com.tr.ibs.entity.UserObject;
import com.tr.ibs.portal.dao.ApplicationDao;
import com.tr.ibs.portal.dao.RoleDao;
import com.tr.ibs.portal.model.Application;
import com.tr.ibs.portal.model.Role;
import com.tr.ibs.portal.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private HttpSession session;

	@Override
	public RespData queryListWithPage(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		respData.setRtdata("rows", roleDao.queryListWithPage(params));
		respData.setRtdata("total", roleDao.count(params));
		
		return respData;
	}
	
	@Override
	public RespData getAppRoleTree() {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		List<Map<String, Object>> nodes = new ArrayList<Map<String, Object>>();
		//获取应用
		List<Application> applications = applicationDao.queryAllList();
		for (Application application : applications) {
			Map<String, Object> node = new HashMap<String, Object>();
			Map<String, Object> attributes = new HashMap<String, Object>();
			attributes.put("id", application.getId());
			attributes.put("type", "app");
			node.put("id", application.getId());
			node.put("text", application.getAppname());
			node.put("attributes", attributes);
			node.put("state", "closed");
			//获取应用下的角色
			List<Role> roles = roleDao.queryRolesByAppid(application.getId());
			if (roles != null && roles.size() > 0) {
				List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
				for (Role role : roles) {
					Map<String, Object> c_node = new HashMap<String, Object>();
					Map<String, Object> c_attributes = new HashMap<String, Object>();
					c_attributes.put("id", role.getId());
					c_attributes.put("type", "role");
					c_node.put("id", role.getId());
					c_node.put("text", role.getRolename());
					c_node.put("attributes", c_attributes);
					children.add(c_node);
				}
				node.put("children", children);
			}
			nodes.add(node);
		}
		respData.setRtdata("tree", nodes);
		return respData;
	}
	
	@Override
	public RespData queryRoles4Tree(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		
		if (params.get("appid") == null) {
			respData.setRtdata("rows", roleDao.queryListWithPage(params));
			respData.setRtdata("total", roleDao.count(params));
		} else {
			respData.setRtdata("rows", roleDao.queryRolesByAppidWithPage(params));
			respData.setRtdata("total", roleDao.countByAppid(params));
		}
		return respData;
	}

	@Override
	public RespData save(Role role) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		try {
			if (role.getId() != null && role.getId() > 0) {
				role.setModifytime(new Date());
				role.setModifier(userObject.getUser().getUsername());
				roleDao.update(role);
			} else {
				role.setDelflag("0");
				role.setCreatetime(new Date());
				role.setCreator(userObject.getUser().getUsername());
				roleDao.insert(role);
			}
		} catch (Exception e) {
			e.printStackTrace();
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

	@Override
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				roleDao.deleteBatch(idArr);
			} 
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

}
