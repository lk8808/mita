package com.tr.ibs.comm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tr.ibs.comm.dao.DictDao;
import com.tr.ibs.comm.model.Dict;
import com.tr.ibs.comm.service.IDictService;
import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;

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
	public RespData queryDicttypesWithPage(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		int page = (int)params.get("page");
		int rows = (int)params.get("rows");
		params.put("begin", (page-1)*rows);
		respData.setRtdata("rows", dictDao.queryDicttypesWithPage(params));
		respData.setRtdata("total", dictDao.countDicttypes(params));
		
		return respData;
	}

}
