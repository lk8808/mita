package com.tr.ibs.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.service.IUserService;

@RestController
@RequestMapping("/base")
public class BaseController {
	
	@Autowired
	private IUserService userService;
	
	@RequestMapping("/getSession")
	public RespData getSession() {
		return userService.getSession();
	}
}
