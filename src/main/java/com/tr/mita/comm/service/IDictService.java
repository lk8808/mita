package com.tr.mita.comm.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.model.Dict;
import com.tr.mita.entity.RespData;

public interface IDictService {

	public RespData queryDictsByDicttypeid(String dicttypeid);
	
	public Dict getDict(String dicttypeid, String dictid);
	
	public RespData queryDicttypesWithPage(Map<String, Object> params);
}
