package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.UserObject;
import com.tr.mita.portal.model.User;

public interface IUserService {
	
	public List<User> getAllUsers();
	
	public Map<String, Object> loginVerify (Map<String, Object> map) throws Exception;

	public void logout();
	
	public User getUserByUserName(String username);
	
	public UserObject getCurrentUser();
	
	public Map<String, Object> queryAllUsersByDepid(Map<String, Object> params);
	
	public Integer save(User user) throws Exception;
	public Integer resetPasswd(String ids);
	
	public Integer modifyPwd(String oldpasswd, String newpasswd) throws Exception;

	public Integer modifyTheme(String theme);

}
