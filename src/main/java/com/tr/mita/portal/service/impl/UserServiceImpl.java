package com.tr.mita.portal.service.impl;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
import com.tr.mita.org.dao.EmployeeDao;
import com.tr.mita.org.model.Employee;
import com.tr.mita.portal.dao.UserDao;
import com.tr.mita.portal.model.User;
import com.tr.mita.portal.service.IUserService;
import com.tr.mita.utils.MD5Util;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
    private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public List<User> getAllUsers() {
		return userDao.queryAllList();
	}

	@Override
	public RespData loginVerify(Map<String, Object> map) {
		
		RespData respData = new RespData();
		
		String username = (String)map.get("username");
		String passwd = (String)map.get("passwd");
		
		User user = userDao.getUserByUsername(username);
		if (user == null) {
			respData.setRtsts(new Rtsts("100001", "用户名不存在"));
			return respData;
		}
		if ( !passwd.equals(user.getPasswd())) {
			respData.setRtsts(new Rtsts("100002", "密码错误"));
			return respData;
		}
		respData.setRtsts(new Rtsts("000000", "登陆成功！"));
		//保存session信息
		UserObject userObject = new UserObject();
		user.setPasswd("");
		userObject.setUser(user);
		Employee employee = employeeDao.get(user.getEmployeeid());
		userObject.setEmployee(employee);
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		redisUtil.set(token, userObject, 2l, TimeUnit.HOURS);
		respData.setRtdata("token", token);
		respData.setRtdata("empname", employee.getEmpname());
		respData.setRtdata("theme", user.getDesktopstyle());

		return respData;
	}

	@Override
	public RespData logout() {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		if (token != null) {
			redisUtil.remove(token);
		}
		return respData;
	}

	@Override
	public User getUserByUserName(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public RespData getCurrentUser() {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		if (token != null) {
			UserObject userObject = (UserObject)redisUtil.get(token);
			respData.setRtdata("userObject", userObject);
			return respData;
		}
		return respData;
	}

	@Override
	public RespData queryAllUsersByDepid(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		//设置分页
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		if (depid > 0) {
			params.put("depid", depid);
			respData.setRtdata("rows", userDao.queryAllUsersByDepidWithPage(params));
			respData.setRtdata("total", userDao.countAllUsersByDepid(params));
		} else {
			respData.setRtdata("rows", userDao.queryListWithPage(params));
			respData.setRtdata("total", userDao.count(params));
		}
		return respData;
	}

	@Override
	public RespData save(User user) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		String token = request.getHeader("Token");

		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			user.setModifier(userObject.getUser().getUsername());
			user.setModifytime(new Date());
			userDao.update(user);
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

	@Override
	public boolean isUnique(User user) {
		int tmpId = user.getId();
		user.setId(null);
		User tmp = userDao.expand(user);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

	@Override
	public RespData resetPasswd(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		String token = request.getHeader("Token");
		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			String[] idArr = ids.split(",");
			for (String id : idArr) {
				User user = new User();
				user.setId(Integer.valueOf(id));
				user.setPasswd(MD5Util.md5Encode("000000"));
				user.setModifier(userObject.getUser().getUsername());
				user.setModifytime(new Date());
				userDao.update(user);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}
	
	@Override
	public RespData modifyPwd(String oldpasswd, String newpasswd){
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		String token = request.getHeader("Token");
		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			User user = this.userDao.get(userObject.getUser().getId());
			if (!user.getPasswd().equals(oldpasswd)) {
				respData.setRtsts(new Rtsts("200001", "原始密码错误！"));
				return respData;
			}
			user.setId(userObject.getUser().getId());
			user.setPasswd(newpasswd);
			user.setModifier(userObject.getUser().getUsername());
			user.setModifytime(new Date());
			userDao.update(user);
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	};

	@Override
	public RespData modifyTheme(String theme) {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			User user = this.userDao.get(userObject.getUser().getId());
			user.setId(userObject.getUser().getId());
			user.setDesktopstyle(theme);
			user.setModifier(userObject.getUser().getUsername());
			user.setModifytime(new Date());
			userDao.update(user);
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "修改失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}
}
