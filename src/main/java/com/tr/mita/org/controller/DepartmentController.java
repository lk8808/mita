package com.tr.mita.org.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Department;
import com.tr.mita.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.mita.entity.RespData;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/getDepTree")
	public RespData getDepTree() {
		return departmentService.getDepartmentTree();
	}
	
	@RequestMapping("/queryAllDepsByParentid")
	public RespData queryAllDepsByParentid(@RequestBody Map<String, Object> params) {
		return departmentService.queryAllDepsByParentid(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Department department) {
		return departmentService.save(department);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return departmentService.del(ids);
	}

}
