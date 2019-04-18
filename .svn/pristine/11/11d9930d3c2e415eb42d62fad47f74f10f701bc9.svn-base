package com.tr.ibs.portal.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.model.Role;
import com.tr.ibs.portal.service.IRoleService;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value="/getAppRoleTree")
	public RespData getAppRoleTree() {
		return roleService.getAppRoleTree();
	}
	
	@RequestMapping(value="/queryRoles4Tree")
	public RespData queryRoles4Tree(@RequestBody Map<String, Object> params) {
		return roleService.queryRoles4Tree(params);
	}
	
	@RequestMapping(value="/queryListWithPage")
	public RespData queryListWithPage(@RequestBody Map<String, Object> params) {
		return roleService.queryListWithPage(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(Role role) {
		return roleService.save(role);
	}
	
	@RequestMapping(value="del")
	public RespData del(String ids) {
		return roleService.del(ids);
	}

}
