package com.tr.mita.portal.controller;

import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Role;
import com.tr.mita.portal.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
	
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping(value="/queryList")
	public RespData queryListWithPage(@RequestBody Map<String, Object> params) {
		return roleService.queryListWithPage(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	@RequestMapping(value="del")
	public RespData del(String ids) {
		return roleService.del(ids);
	}

}
