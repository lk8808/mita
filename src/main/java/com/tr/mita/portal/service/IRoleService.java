package com.tr.mita.portal.service;

import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Role;

public interface IRoleService {

	public RespData queryListWithPage(Map<String, Object> params);
	
	public RespData save(Role role);
	
	public RespData del(String ids);
	
}
