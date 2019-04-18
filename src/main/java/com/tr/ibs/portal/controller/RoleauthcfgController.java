package com.tr.ibs.portal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.service.IRoleauthcfgService;

@RestController
@RequestMapping("/roleauthcfg")
public class RoleauthcfgController {
	
	@Autowired
	private IRoleauthcfgService roleauthcfgService;
	
	@RequestMapping("/queryRoleauthcfgsByRoleid")
	public List<Map<String, Object>> queryRoleauthcfgsByRoleid(Integer roleid) {
		return roleauthcfgService.queryRoleauthcfgsByRoleid(roleid);
	}
	
	@RequestMapping("/saveRoleauthcfgByRole")
	public RespData saveRoleauthcfgByRole(@RequestBody Map<String, Object> params) {
		return roleauthcfgService.saveRoleauthcfgByRole(params);
	}

}
