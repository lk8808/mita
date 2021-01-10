package com.tr.mita.org.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.org.model.Empposlnk;

@Mapper
public interface EmpposlnkDao extends IBaseDao<Empposlnk> {
	
	public int insertByDepidPosids(Map<String, Object> params);

	public List<Integer> queryPosidsByEmpid(int empid);

	public int deleteByEmpid(int empid);
	
}
