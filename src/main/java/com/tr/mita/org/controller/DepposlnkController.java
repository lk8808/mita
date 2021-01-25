package com.tr.mita.org.controller;

import com.tr.mita.org.service.IDepposlnkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/depposlnk")
public class DepposlnkController {
	
	@Autowired
	private IDepposlnkService depposlnkService;

	@RequestMapping("/queryByDepid")
	public List<Map<String, Object>> queryByDepid(Long depid) {
		return depposlnkService.queryByDepid(depid);
	}

	@RequestMapping("/queryAllList")
	public List<Map<String, Object>> queryAllList() {
		return depposlnkService.queryAllList();
	}

}
