package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public RespData loginVerify(Map<String, Object> map);

	public RespData logout();
	
	public User getUserByUserName(String username);
	
	public RespData getCurrentUser();
	
	public RespData queryAllUsersByDepid(Map<String, Object> params);
	
	public RespData save(User user);
	
	public boolean isUnique(User user);
	
	public RespData resetPasswd(String ids);
	
	public RespData modifyPwd(String oldpasswd, String newpasswd);

	public RespData modifyTheme(String theme);

}
