package com.tr.mita.portal.controller;

import java.util.Map;

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
	public Map<String, Object> queryListWithPage(@RequestBody Map<String, Object> params) {
		return roleService.queryListWithPage(params);
	}
	
	@RequestMapping(value="/save")
	public Integer save(@RequestBody Role role) throws Exception {
		return roleService.save(role);
	}
	
	@RequestMapping(value="del")
	public Integer del(String ids) {
		return roleService.del(ids);
	}

}
