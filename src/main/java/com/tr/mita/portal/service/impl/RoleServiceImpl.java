package com.tr.mita.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import com.tr.mita.org.model.Employee;
import com.tr.mita.portal.dao.ApplicationDao;
import com.tr.mita.portal.dao.RoleDao;
import com.tr.mita.portal.model.Application;
import com.tr.mita.portal.model.Role;
import com.tr.mita.portal.service.IRoleService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private RoleDao roleDao;

	@Override
	public RespData queryListWithPage(Map<String, Object> params) {
		RespData respData = new RespData();

		respData.setRtdata("bizdatas", roleDao.queryListWithPage(params));
		respData.setRtdata("total", roleDao.count(params));
		return respData;
	}

	@Override
	public RespData save(Role role) {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		try {
			Role tmp = new Role();
			tmp.setId(role.getId());
			tmp.setRoleno(role.getRoleno());
			if (!isUnique(tmp)) {
				respData.setRtsts(new Rtsts("200001", "角色编号不唯一！"));
				return respData;
			}
			UserObject userObject = (UserObject)redisUtil.get(token);
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
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

	@Override
	public RespData del(String ids) {
		RespData respData = new RespData();
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				roleDao.deleteBatch(idArr);
			} 
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	private boolean isUnique(Role role) {
		RespData respData = new RespData();
		Integer tmpId = role.getId();
		role.setId(null);
		Role tmp = roleDao.expand(role);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}
}
