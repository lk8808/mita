package com.tr.ibs.org.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Department;
import com.tr.ibs.org.service.IDepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/get")
	public Department get(int id) {
		return departmentService.get(id);
	}
	
	@RequestMapping("/getDepTree")
	public List<Map<String, Object>> getDepTree() {
		return departmentService.getDepartmentTree();
	}
	
	@RequestMapping("/queryAllDepsByParentid")
	public RespData queryAllDepsByParentid(@RequestBody Map<String, Object> params) {
		return departmentService.queryAllDepsByParentid(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(Department department, String posids) {
		return departmentService.save(department, posids);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return departmentService.del(ids);
	}
	
	@RequestMapping(value="/unique")
	public boolean isUnique(Department department) {
		return departmentService.isUnique(department);
	}
}
