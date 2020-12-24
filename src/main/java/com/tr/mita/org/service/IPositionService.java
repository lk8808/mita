package com.tr.mita.org.service;

import java.util.Map;

import com.tr.mita.org.model.Position;
import org.springframework.web.bind.annotation.RequestBody;

import com.tr.mita.entity.RespData;

public interface IPositionService {
	
	public Position get(Integer id);

	public RespData queryListWithPage(@RequestBody Map<String, Object> params);

	public RespData queryAllList();
	
	public RespData save(Position position);
	
	public RespData del(String ids);
}
