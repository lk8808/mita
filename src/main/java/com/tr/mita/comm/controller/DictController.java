package com.tr.mita.comm.controller;

import com.tr.mita.comm.model.Dict;
import com.tr.mita.comm.service.IDictService;
import com.tr.mita.entity.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
	public RespData queryDictsByDicttypeid(String dicttypeid) {
		return dictService.queryDictsByDicttypeid(dicttypeid);
	}
	
	@RequestMapping("/queryDicttypesWithPage")
	public RespData queryDicttypesWithPage(@RequestBody Map<String, Object> params) {
		return dictService.queryDicttypesWithPage(params);
	}
}
