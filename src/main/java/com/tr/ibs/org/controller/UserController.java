package com.tr.ibs.org.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.User;
import com.tr.ibs.org.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	@RequestMapping("/getCurrentUser")
	public User getCurrentUser() {
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
}
