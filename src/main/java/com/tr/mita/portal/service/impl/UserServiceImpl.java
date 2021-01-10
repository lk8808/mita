package com.tr.mita.portal.service.impl;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.Rtsts;
import com.tr.mita.comm.entity.UserObject;
import com.tr.mita.comm.exception.RespException;
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
	public Map<String, Object> loginVerify(Map<String, Object> map) throws Exception {
		Map<String, Object> retMap = new HashMap<>();
		
		String username = (String)map.get("username");
		String passwd = (String)map.get("passwd");
		
		User user = userDao.getUserByUsername(username);
		if (user == null) {
			throw new RespException("100001", "用户名不存在");
		}
		if ( !passwd.equals(user.getPasswd())) {
			throw new RespException("100001", "密码错误");
		}
		//保存session信息
		UserObject userObject = new UserObject();
		user.setPasswd("");
		userObject.setUser(user);
		Employee employee = employeeDao.get(user.getEmployeeid());
		userObject.setEmployee(employee);
		String token = "token_" + UUID.randomUUID().toString().replaceAll("-", "");
		redisUtil.set(token, userObject, 2l, TimeUnit.HOURS);
		retMap.put("token", token);
		retMap.put("empname", employee.getEmpname());
		retMap.put("theme", user.getDesktopstyle());

		return retMap;
	}

	@Override
	public void logout() {
		String token = request.getHeader("Token");
		if (token != null) {
			redisUtil.remove(token);
		}
	}

	@Override
	public User getUserByUserName(String username) {
		return userDao.getUserByUsername(username);
	}

	@Override
	public UserObject getCurrentUser() {
		String token = request.getHeader("Token");
		if (token != null) {
			UserObject userObject = (UserObject)redisUtil.get(token);
			return userObject;
		}
		return null;
	}

	@Override
	public Map<String, Object> queryAllUsersByDepid(Map<String, Object> params) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		//设置分页
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		int depid = params.get("depid") == null ? 0 : (int)params.get("depid");
		if (depid > 0) {
			params.put("depid", depid);
			retMap.put("rows", userDao.queryAllUsersByDepidWithPage(params));
			retMap.put("total", userDao.countAllUsersByDepid(params));
		} else {
			retMap.put("rows", userDao.queryListWithPage(params));
			retMap.put("total", userDao.count(params));
		}
		return retMap;
	}

	@Override
	public Integer save(User user) throws Exception {
		String token = request.getHeader("Token");
		UserObject userObject = (UserObject)redisUtil.get(token);
		User tmp = new User();
		tmp.setId(user.getId());
		tmp.setUsername(user.getUsername());
		if (!isUnique(tmp)) {
			throw new RespException("200001", "用户名不唯一！");
		}
		user.setModifier(userObject.getUser().getUsername());
		user.setModifytime(new Date());
		return userDao.update(user);
	}

	@Override
	public Integer resetPasswd(String ids) {
		String token = request.getHeader("Token");
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
		return idArr.length;
	}
	
	@Override
	public Integer modifyPwd(String oldpasswd, String newpasswd) throws Exception{
		String token = request.getHeader("Token");
		UserObject userObject = (UserObject)redisUtil.get(token);
		User user = this.userDao.get(userObject.getUser().getId());
		if (!user.getPasswd().equals(oldpasswd)) {
			throw new RespException("200001", "原始密码错误！");
		}
		user.setId(userObject.getUser().getId());
		user.setPasswd(newpasswd);
		user.setModifier(userObject.getUser().getUsername());
		user.setModifytime(new Date());
		return userDao.update(user);
	};

	@Override
	public Integer modifyTheme(String theme) {
		String token = request.getHeader("Token");
		UserObject userObject = (UserObject)redisUtil.get(token);
		User user = this.userDao.get(userObject.getUser().getId());
		user.setId(userObject.getUser().getId());
		user.setDesktopstyle(theme);
		user.setModifier(userObject.getUser().getUsername());
		user.setModifytime(new Date());
		return userDao.update(user);
	}

	private boolean isUnique(User user) {
		int tmpId = user.getId();
		user.setId(null);
		User tmp = userDao.expand(user);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}
}
