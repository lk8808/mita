package com.tr.mita.portal.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.service.IRoleauthcfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
