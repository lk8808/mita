package com.tr.ibs.portal.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.UserObject;
import com.tr.ibs.portal.dao.HomecfgDao;
import com.tr.ibs.portal.service.IHomecfgService;

@Service
@Transactional
public class HomecfgServiceImpl implements IHomecfgService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HomecfgDao homecfgDao;
	
	@Autowired
	private HttpSession session;
	
	public List<Map<String, Object>> queryMyhomecfg(){
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		return homecfgDao.queryMyhomecfg(userObject.getUser().getId().toString());
	};
	
}
