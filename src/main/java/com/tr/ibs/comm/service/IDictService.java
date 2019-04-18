package com.tr.ibs.comm.service;

import java.util.List;
import java.util.Map;

import com.tr.ibs.comm.model.Dict;
import com.tr.ibs.entity.RespData;

public interface IDictService {

	public List<Dict> queryDictsByDicttypeid(String dicttypeid);
	
	public Dict getDict(String dicttypeid, String dictid);
	
	public RespData queryDicttypesWithPage(Map<String, Object> params);
}
