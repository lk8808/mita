package com.tr.ibs.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.model.Roleauthcfg;

public interface IRoleauthcfgService {

	public List<Map<String, Object>> queryRoleauthcfgsByRoleid(Integer roleid);
	
	public RespData saveRoleauthcfgByRole(Map<String, Object> params);
}
