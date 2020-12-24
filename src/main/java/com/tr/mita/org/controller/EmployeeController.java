package com.tr.mita.org.controller;

import java.util.Map;

import com.tr.mita.org.model.Employee;
import com.tr.mita.org.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.mita.entity.RespData;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService employeeService;
	
	@RequestMapping("/get")
	public Employee get(int id) {
		return employeeService.get(id);
	}
	
	@RequestMapping("/queryEmpsByDepid")
	public RespData queryEmpsByDepid(@RequestBody Map<String, Object> params) {
		return employeeService.queryEmpsByDepid(params);
	}
	
	@RequestMapping("/queryAllEmpsByDepid")
	public RespData queryAllEmpsByDepid(@RequestBody Map<String, Object> params) {
		return employeeService.queryAllEmpsByDepid(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Employee employee) {
		return employeeService.save(employee);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return employeeService.del(ids);
	}

}
