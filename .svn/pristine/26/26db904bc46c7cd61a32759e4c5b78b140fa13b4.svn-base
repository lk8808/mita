package com.tr.ibs.org.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Employee;
import com.tr.ibs.org.service.IEmployeeService;

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
	public RespData save(Employee employee, String depposlnkids) {
		return employeeService.save(employee, depposlnkids);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return employeeService.del(ids);
	}
	
	@RequestMapping(value="/unique")
	public boolean isUnique(Employee employee) {
		return employeeService.isUnique(employee);
	}
	
	@RequestMapping("/getMyInfo")
	public Employee getMyInfo() {
		return employeeService.getMyInfo();
	}
}
