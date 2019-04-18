package com.tr.ibs.portal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.portal.service.IHomecfgService;

@RestController
@RequestMapping("/homecfg")
public class HomecfgController {

	@Autowired
	private IHomecfgService homecfgService;
	
	
	@RequestMapping("/queryMyhomecfg")
	public List<Map<String, Object>> queryMyhomecfg() {
		return homecfgService.queryMyhomecfg();
	}
	
}
