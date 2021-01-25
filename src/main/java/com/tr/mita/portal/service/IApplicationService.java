package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.portal.model.Application;

public interface IApplicationService {
	
	public List<Application> queryAllList();
	
	public Map<String, Object> queryAllAuthList();

	public Map<String, Object> queryListWithPage(Map<String, Object> map);
	
	public Integer save(Application application) throws Exception;
	
	public Integer del(String ids);

}
