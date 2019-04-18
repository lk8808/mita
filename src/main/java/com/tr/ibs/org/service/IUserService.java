package com.tr.ibs.org.service;

import java.util.List;
import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public RespData loginVerify(Map<String, Object> map);
	
	public User getUserByUserName(String username);
	
	public User getCurrentUser();
	
	public RespData queryAllUsersByDepid(Map<String, Object> params);
	
	public RespData save(User user);
	
	public boolean isUnique(User user);
	
	public RespData resetPasswd(String ids);
	
	public RespData getSession();

}
