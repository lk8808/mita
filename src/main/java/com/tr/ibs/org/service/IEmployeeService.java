package com.tr.ibs.org.service;

import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Employee;

public interface IEmployeeService {

	public Employee get(int id);
	
	public RespData queryEmpsByDepid(Map<String, Object> params);
	
	public RespData queryAllEmpsByDepid(Map<String, Object> params);
	
	public RespData save(Employee employee, String depposlnkids);
	
	public RespData del(String ids);
	
	public boolean isUnique(Employee employee);
	
	public Employee getMyInfo();
}
