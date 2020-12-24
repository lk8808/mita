package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Application;

public interface IApplicationService {
	
	public RespData queryAllList();
	
	public RespData queryAllAuthList();

	public RespData queryListWithPage(Map<String, Object> map);
	
	public RespData save(Application application);
	
	public RespData del(String ids);
}
