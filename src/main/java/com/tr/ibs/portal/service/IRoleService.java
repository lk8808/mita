package com.tr.ibs.portal.service;

import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.model.Role;

public interface IRoleService {

	public RespData queryListWithPage(Map<String, Object> params);
	
	public RespData getAppRoleTree();
	
	public RespData queryRoles4Tree(Map<String, Object> params);
	
	public RespData save(Role role);
	
	public RespData del(String ids);
	
}
