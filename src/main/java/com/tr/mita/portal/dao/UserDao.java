package com.tr.mita.portal.dao;

import com.tr.mita.base.IBaseDao;
import com.tr.mita.portal.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends IBaseDao<User> {
	
	public User getUserByUsername(String username);
	
	public List<User> queryAllUsersByDepidWithPage(Map<String, Object> params);
	
	public int countAllUsersByDepid(Map<String, Object> params);
	
	public int updateByEmployeeid(User user);
	
	public int delBatchByEmpids(String empids);
	
	public int resetPasswd(String ids);
	
}
