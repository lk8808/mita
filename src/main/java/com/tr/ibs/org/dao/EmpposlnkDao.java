package com.tr.ibs.org.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.org.model.Empposlnk;

@Mapper
public interface EmpposlnkDao extends IBaseDao<Empposlnk> {
	
	public int insertByDepposlnk(Map<String, Object> params);

	public int deleteByEmpid(int empid);
	
}
