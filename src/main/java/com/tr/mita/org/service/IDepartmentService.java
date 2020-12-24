package com.tr.mita.org.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.org.model.Department;

public interface IDepartmentService {
	
	public RespData queryAllDepsByParentid(Map<String, Object> params);
	
	public RespData getDepartmentTree();
	
	public RespData save(Department department);
	
	public RespData del(String ids);
	
}
