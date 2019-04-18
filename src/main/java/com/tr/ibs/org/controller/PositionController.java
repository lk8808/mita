package com.tr.ibs.org.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Position;
import com.tr.ibs.org.service.IPositionService;

@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private IPositionService positionService;
	
	@RequestMapping("/get")
	public Position get(Integer id) {
		return positionService.get(id);
	}
	
	@RequestMapping("/queryPositions")
	public RespData queryPositions(@RequestBody Map<String, Object> params) {
		return positionService.queryPositions(params);
	}
	
	@RequestMapping(value="/save")
	public RespData save(Position position) {
		return positionService.save(position);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return positionService.del(ids);
	}
	
	@RequestMapping(value="/unique")
	public boolean isUnique(Position position) {
		return positionService.isUnique(position);
	}
}
