package com.tr.ibs.org.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.org.model.Depposlnk;

public interface IDepposlnkService {

	public List<Depposlnk> queryByDepid(Integer depid);
	
	public RespData queryByDepid_ext(@RequestBody Map<String, Object> params);
	
	public List<Map<String, Object>> getDepposTree();
	
	public List<Map<String, Object>> getDepposTreeByDepid(Integer depid);
}
