package com.tr.mita.org.controller;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Position;
import com.tr.mita.org.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private IPositionService positionService;

	@RequestMapping("/queryList")
	public Map<String, Object> queryList(@RequestBody Map<String, Object> params) {
		return positionService.queryListWithPage(params);
	}

	@RequestMapping("/queryAllList")
	public List<Position> queryAllList() {
		return positionService.queryAllList();
	}
	
	@RequestMapping(value="/save")
	public Integer save(@RequestBody Position position) throws Exception {
		return positionService.save(position);
	}
	
	@RequestMapping(value="/del")
	public Integer del(String ids) {
		return positionService.del(ids);
	}
}
