package com.tr.mita.org.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Employee;

public interface IEmployeeService {

	public Employee get(int id);

	public List<Employee> queryAllList();
	
	public Map<String, Object> queryEmpsByDepid(Map<String, Object> params);
	
	public Map<String, Object> queryAllEmpsByDepid(Map<String, Object> params);
	
	public Integer save(Employee employee) throws Exception;
	
	public Integer del(String ids);
}
