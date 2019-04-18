package com.tr.ibs.org.service;

import java.util.List;
import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Department;

public interface IDepartmentService {
	
	public Department get(int id);

	public List<Department> queryDepartmentsLevel1();
	
	public List<Department> queryDepartmentsByParentid(int parentid);
	
	public RespData queryAllDepsByParentid(Map<String, Object> params);
	
	public List<Map<String, Object>> getDepartmentTree();
	
	public List<Map<String, Object>> getDepartmentTreeByParentid(int parentid);
	
	public RespData save(Department department, String posids);
	
	public RespData del(String ids);
	
	public boolean isUnique(Department department);
	
}
