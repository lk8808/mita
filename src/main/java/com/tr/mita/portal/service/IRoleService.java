package com.tr.mita.portal.service;

import java.util.Map;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.portal.model.Role;

public interface IRoleService {

	public Map<String, Object> queryListWithPage(Map<String, Object> params);
	
	public Integer save(Role role) throws Exception;
	
	public Integer del(String ids);
	
}
