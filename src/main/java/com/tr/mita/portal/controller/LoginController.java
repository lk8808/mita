package com.tr.mita.portal.controller;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.portal.service.IUserService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
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
	public RespData login(@RequestBody Map<String, Object> map) {
		return userService.loginVerify(map);
	}
	
	@PostMapping(value="/logout")
	public RespData logout() {
		return userService.logout();
	}

}
