package com.tr.mita.org.controller;

import java.util.Map;

import com.tr.mita.org.model.Position;
import com.tr.mita.org.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.mita.entity.RespData;

@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private IPositionService positionService;
	
	@RequestMapping("/get")
	public Position get(Integer id) {
		return positionService.get(id);
	}
	
	@RequestMapping("/queryList")
	public RespData queryList(@RequestBody Map<String, Object> params) {
		return positionService.queryListWithPage(params);
	}

	@RequestMapping("/queryAllList")
	public RespData queryAllList() {
		return positionService.queryAllList();
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Position position) {
		return positionService.save(position);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return positionService.del(ids);
	}
}
