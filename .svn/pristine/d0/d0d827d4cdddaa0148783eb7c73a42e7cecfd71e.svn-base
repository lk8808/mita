package com.tr.ibs.portal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.service.IRecordlogService;

@RestController
@RequestMapping("/recordlog")
public class RecordlogController {

	@Autowired
	private IRecordlogService recordlogService;
	
	@RequestMapping("/addRecordlog")
	public RespData addRecordlog(String recordid,String recordtype) {
		return recordlogService.addRecordlog(recordid,recordtype);
	}
	
	@RequestMapping(value="/queryRecordlogByPage")
	public RespData queryRecordlogByPage(@RequestBody Map<String, Object> map) {
		return recordlogService.queryRecordlogByPage(map);
	}
	
	@RequestMapping(value="/queryRecordlogFetch10")
	public RespData queryRecordlogFetch10(@RequestBody Map<String, Object> map) {
		return recordlogService.queryRecordlogFetch10(map);
	}
	
}
