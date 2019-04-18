package com.tr.ibs.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.org.model.Employee;

@Mapper
public interface EmployeeDao extends IBaseDao<Employee> {
	
	public List<Employee> queryEmpsByDepidWithPage(Map<String, Object> params);
	
	public int countEmpsByDepid(Map<String, Object> params);

	public List<Employee> queryAllEmpsByDepidWithPage(Map<String, Object> params);
	
	public int countAllEmpsByDepid(Map<String, Object> params);
	
	public int deleteBatch(String[] ids);
}
