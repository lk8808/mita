package com.tr.ibs.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.model.Application;

public interface IApplicationService {
	
	public List<Application> queryAllList();
	
	public List<Application> queryAllAuthList();

	public RespData queryApplicationsWithPage(Map<String, Object> map);
	
	public RespData save(Application application);
	
	public RespData del(String ids);
}
