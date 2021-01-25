package com.tr.mita.org.dao;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.org.model.Depposlnk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepposlnkDao extends IBaseDao<Depposlnk> {
	
	public List<Map<String, Object>> queryByDepid(Long depid);

	public List<Map<String, Object>> queryAllList_ext();
	
	public int insertByDepidPosids(Map<String, Object> params);

	public int deleteByDepid(long depid);
}
