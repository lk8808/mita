package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;

public interface IHomecfgService {
	
	public List<Map<String, Object>> queryMyhomecfg();
	
	public RespData saveHomecfg(Map<String, Object> params);
}
