package com.tr.mita.org.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Department;
import com.tr.mita.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	private IDepartmentService departmentService;
	
	@RequestMapping("/getDepTree")
	public List<Map<String, Object>> getDepTree() {
		return departmentService.getDepTree();
	}
	
	@RequestMapping("/queryAllDepsByParentid")
	public Map<String, Object> queryAllDepsByParentid(@RequestBody Map<String, Object> params) {
		return departmentService.queryAllDepsByParentid(params);
	}
	
	@RequestMapping(value="/save")
	public Integer save(@RequestBody Department department) throws Exception {
		return departmentService.save(department);
	}
	
	@RequestMapping(value="/del")
	public Integer del(String ids) {
		return departmentService.del(ids);
	}

}
