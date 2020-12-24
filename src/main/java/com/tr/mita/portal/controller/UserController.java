package com.tr.mita.portal.controller;

import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.User;
import com.tr.mita.portal.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	@RequestMapping("/getCurrentUser")
	public RespData getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@RequestMapping("/queryAllUsersByDepid")
	public RespData queryAllUsersByDepid(@RequestBody Map<String, Object> params) {
		return userService.queryAllUsersByDepid(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(User user) {
		return userService.save(user);
	}
	
	@RequestMapping(value="/unique")
	public boolean isUnique(User user) {
		return userService.isUnique(user);
	}
	
	@RequestMapping(value="/resetPasswd")
	public RespData resetPasswd(String ids) {
		return userService.resetPasswd(ids);
	}
	
	@RequestMapping(value="/modifyPwd")
	public RespData modifyPwd(String op, String np) {
		return userService.modifyPwd(op, np);
	}

	@RequestMapping(value="/modifyTheme")
	public RespData modifyTheme(String theme) {
		return userService.modifyTheme(theme);
	}
	
}
