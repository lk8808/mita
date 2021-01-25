package com.tr.mita.comm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tr.mita.comm.dao.DictDao;
import com.tr.mita.comm.model.Dict;
import com.tr.mita.comm.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictServiceImpl implements IDictService {
	
	@Autowired
	private DictDao dictDao;

	@Override
	public List<Dict> queryDictsByDicttypeid(String dicttypeid) {
		return dictDao.queryDictsByDicttypeid(dicttypeid);
	}

	@Override
	public Dict getDict(String dicttypeid, String dictid) {
		return dictDao.get(dicttypeid, dictid);
	}

	@Override
	public Map<String, Object> queryDicttypesWithPage(Map<String, Object> params) {
		Map<String, Object> retMap = new HashMap<>();
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		retMap.put("rows", dictDao.queryDicttypesWithPage(params));
		retMap.put("total", dictDao.countDicttypes(params));
		
		return retMap;
	}

}
