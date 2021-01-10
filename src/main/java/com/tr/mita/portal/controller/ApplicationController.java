package com.tr.mita.portal.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.entity.RespData;
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
	public List<Application> queryAllList() {
		return applicationService.queryAllList();
	}
	
	@RequestMapping(value="/queryAllAuthList")
	public Map<String, Object> queryAllAuthList() {
		return applicationService.queryAllAuthList();
	}
	
	@RequestMapping(value="/queryList")
	public Map<String, Object> queryList(@RequestBody Map<String, Object> map) {
		return applicationService.queryListWithPage(map);
	}
	
	@RequestMapping(value="/save")
	public Integer save(@RequestBody Application application) throws Exception {
		return applicationService.save(application);
	}
	
	@RequestMapping(value="/del")
	public Integer del(String ids) {
		return applicationService.del(ids);
	}
}
