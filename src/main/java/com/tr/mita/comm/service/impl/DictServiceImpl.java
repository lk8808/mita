package com.tr.mita.comm.service.impl;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.dao.DictDao;
import com.tr.mita.comm.model.Dict;
import com.tr.mita.comm.service.IDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;

@Service
public class DictServiceImpl implements IDictService {
	
	@Autowired
	private DictDao dictDao;

	@Override
	public RespData queryDictsByDicttypeid(String dicttypeid) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", dictDao.queryDictsByDicttypeid(dicttypeid));
		return respData;
	}

	@Override
	public Dict getDict(String dicttypeid, String dictid) {
		return dictDao.get(dicttypeid, dictid);
	}

	@Override
	public RespData queryDicttypesWithPage(Map<String, Object> params) {
		RespData respData = new RespData();
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		respData.setRtdata("rows", dictDao.queryDicttypesWithPage(params));
		respData.setRtdata("total", dictDao.countDicttypes(params));
		
		return respData;
	}

}
