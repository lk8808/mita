package com.tr.mita.portal.controller;

import java.util.Map;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.comm.entity.UserObject;
import com.tr.mita.portal.model.User;
import com.tr.mita.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;
	
	
	@RequestMapping("/getCurrentUser")
	public UserObject getCurrentUser() {
		return userService.getCurrentUser();
	}
	
	@RequestMapping("/queryAllUsersByDepid")
	public Map<String, Object> queryAllUsersByDepid(@RequestBody Map<String, Object> params) {
		return userService.queryAllUsersByDepid(params);
	}
	
	@RequestMapping(value="/save")
	public Integer save(User user) throws Exception {
		return userService.save(user);
	}

	@RequestMapping(value="/resetPasswd")
	public Integer resetPasswd(String ids) {
		return userService.resetPasswd(ids);
	}
	
	@RequestMapping(value="/modifyPwd")
	public Integer modifyPwd(String op, String np) throws Exception {
		return userService.modifyPwd(op, np);
	}

	@RequestMapping(value="/modifyTheme")
	public Integer modifyTheme(String theme) {
		return userService.modifyTheme(theme);
	}
	
}
