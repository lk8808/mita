package com.tr.mita.org.service;

import java.util.List;
import java.util.Map;

public interface IDepposlnkService {

	public List<Map<String, Object>> queryByDepid(Long depid);

	public List<Map<String, Object>> queryAllList();

}
