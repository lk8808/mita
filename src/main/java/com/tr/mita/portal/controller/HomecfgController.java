package com.tr.mita.portal.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.service.IHomecfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homecfg")
public class HomecfgController {

	@Autowired
	private IHomecfgService homecfgService;
	
	
	@RequestMapping("/queryMyhomecfg")
	public List<Map<String, Object>> queryMyhomecfg() {
		return homecfgService.queryMyhomecfg();
	}
	
	@RequestMapping("/saveHomecfg")
	public RespData saveHomecfg(@RequestBody Map<String, Object> params) {
		return homecfgService.saveHomecfg(params);
	}
}
