package com.tr.mita.org.service;

import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.org.model.Employee;

public interface IEmployeeService {

	public Employee get(int id);
	
	public RespData queryEmpsByDepid(Map<String, Object> params);
	
	public RespData queryAllEmpsByDepid(Map<String, Object> params);
	
	public RespData save(Employee employee);
	
	public RespData del(String ids);
}
