package com.tr.ibs.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.org.model.User;

@Mapper
public interface UserDao extends IBaseDao<User> {
	
	public User getUserByUsername(String username);
	
	public List<User> queryAllUsersByDepidWithPage(Map<String, Object> params);
	
	public int countAllUsersByDepid(Map<String, Object> params);
	
	public int updateByEmployeeid(User user);
	
	public int delBatchByEmpids(String empids);
	
	public int resetPasswd(String ids);
	
}
