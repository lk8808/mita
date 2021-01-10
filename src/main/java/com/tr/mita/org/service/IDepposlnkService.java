package com.tr.mita.org.service;

import com.tr.mita.comm.entity.RespData;

import java.util.List;
import java.util.Map;

public interface IDepposlnkService {

	public List<Map<String, Object>> queryByDepid(Integer depid);

	public List<Map<String, Object>> queryAllList();

}
