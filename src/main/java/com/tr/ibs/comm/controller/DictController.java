package com.tr.ibs.comm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.comm.model.Dict;
import com.tr.ibs.comm.service.IDictService;
import com.tr.ibs.entity.RespData;

@RestController
@RequestMapping("/dict")
public class DictController {

	@Autowired
	private IDictService dictService;
	
	@RequestMapping("/getDict")
	public Dict getDict(String dicttypeid, String dictid) {
		return dictService.getDict(dicttypeid, dictid);
	}
	
	@RequestMapping("/queryDictsByDicttypeid")
	public List<Dict> queryDictsByDicttypeid(String dicttypeid) {
		return dictService.queryDictsByDicttypeid(dicttypeid);
	}
	
	@RequestMapping("/queryDicttypesWithPage")
	public RespData queryDicttypesWithPage(@RequestBody Map<String, Object> params) {
		return dictService.queryDicttypesWithPage(params);
	}
}
