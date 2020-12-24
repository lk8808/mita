package com.tr.mita.portal.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Application;
import com.tr.mita.portal.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class ApplicationController {

	@Autowired
	private IApplicationService applicationService;
	
	@RequestMapping(value="/queryAllList")
	public RespData queryAllList() {
		return applicationService.queryAllList();
	}
	
	@RequestMapping(value="/queryAllAuthList")
	public RespData queryAllAuthList() {
		return applicationService.queryAllAuthList();
	}
	
	@RequestMapping(value="/queryList")
	public RespData queryList(@RequestBody Map<String, Object> map) {
		return applicationService.queryListWithPage(map);
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Application application) {
		return applicationService.save(application);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return applicationService.del(ids);
	}
}
