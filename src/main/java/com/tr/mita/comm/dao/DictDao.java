package com.tr.mita.comm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.comm.model.Dict;

@Mapper
public interface DictDao extends IBaseDao<Dict> {
	
	public Dict get(String dicttypeid, String dictid);

	public List<Dict> queryDictsByDicttypeid(String dicttypeid);
	
	public List<Map<String, Object>> queryDicttypesWithPage(Map<String, Object> params);
	
	public Integer countDicttypes(Map<String, Object> params);
	
}
