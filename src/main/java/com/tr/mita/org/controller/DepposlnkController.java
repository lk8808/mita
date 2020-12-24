package com.tr.mita.org.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Depposlnk;
import com.tr.mita.org.service.IDepposlnkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.mita.entity.RespData;

@RestController
@RequestMapping("/depposlnk")
public class DepposlnkController {
	
	@Autowired
	private IDepposlnkService depposlnkService;

	@RequestMapping("/queryByDepid")
	public RespData queryByDepid(Integer depid) {
		return depposlnkService.queryByDepid(depid);
	}

}
