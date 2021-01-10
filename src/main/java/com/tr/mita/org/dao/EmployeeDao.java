package com.tr.mita.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.org.model.Employee;

@Mapper
public interface EmployeeDao extends IBaseDao<Employee> {
	
	public List<Employee> queryEmpsByDepidWithPage(Map<String, Object> params);
	
	public int countEmpsByDepid(Map<String, Object> params);

	public List<Employee> queryAllEmpsByDepidWithPage(Map<String, Object> params);
	
	public int countAllEmpsByDepid(Map<String, Object> params);
	
	public int deleteBatch(String[] ids);
}
