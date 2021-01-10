package com.tr.mita.portal.controller;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.portal.service.IUserService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequestMapping("/login")
public class LoginController {
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IUserService userService;
	
	@PostMapping(value="/login",consumes="application/json",produces="application/json")
	public Map<String, Object> login(@RequestBody Map<String, Object> map) throws Exception{
		return userService.loginVerify(map);
	}
	
	@PostMapping(value="/logout")
	public void logout() {
		userService.logout();
	}

}
