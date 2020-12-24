package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;

public interface IRoleauthcfgService {

	public List<Map<String, Object>> queryRoleauthcfgsByRoleid(Integer roleid);
	
	public RespData saveRoleauthcfgByRole(Map<String, Object> params);
}
