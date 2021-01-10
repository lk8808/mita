package com.tr.mita.org.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.org.model.Department;

public interface IDepartmentService {

	public List<Map<String, Object>> getDepTree();

	public Map<String, Object> queryAllDepsByParentid(Map<String, Object> params);
	
	public Integer save(Department department) throws Exception;
	
	public Integer del(String ids);
	
}
