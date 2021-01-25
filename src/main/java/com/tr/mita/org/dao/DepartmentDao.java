package com.tr.mita.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.org.model.Department;

@Mapper
public interface DepartmentDao extends IBaseDao<Department> {

	public List<Department> queryDepartmentsLevel1();
	
	public List<Department> queryDepartmentsByParentid(long parentid);
	
	public List<Department> queryAllDepsByParentidWithPage(Map<String, Object> params);
	
	public int countAllDepsByParentid(Map<String, Object> params);

}
