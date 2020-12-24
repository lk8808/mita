package com.tr.mita.org.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tr.mita.org.dao.DepartmentDao;
import com.tr.mita.org.dao.DepposlnkDao;
import com.tr.mita.org.model.Department;
import com.tr.mita.org.model.Depposlnk;
import com.tr.mita.org.service.IDepposlnkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import redis.clients.jedis.ZParams;

@Service
@Transactional
public class DepposlnkServiceImpl implements IDepposlnkService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DepposlnkDao depposlnkDao;
	
	@Autowired
	private DepartmentDao departmentDao;

	@Override
	public RespData queryByDepid(Integer depid) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", depposlnkDao.queryByDepid(depid));
		return respData;
	}

}
