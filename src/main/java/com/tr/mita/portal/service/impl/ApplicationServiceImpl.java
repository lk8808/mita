package com.tr.mita.portal.service.impl;

import com.tr.mita.base.entity.UserObject;
import com.tr.mita.portal.dao.ApplicationDao;
import com.tr.mita.portal.model.Application;
import com.tr.mita.portal.service.IApplicationService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private ApplicationDao applicationDao;
	
	@Override
	public List<Application> queryAllList() {
		return applicationDao.queryAllList();
	}
	
	@Override
	public Map<String, Object> queryAllAuthList() {
		Map<String, Object> retMap = new HashMap<String, Object>();
		String token = request.getHeader("Token");
		if (token != null) {
			UserObject userObject = (UserObject)redisUtil.get(token);
			retMap.put("bizdatas", applicationDao.queryAllAuthList(userObject.getUser().getId().toString()));
			return retMap;
		}
		return null;
	}
	
	@Override
	public Map<String, Object> queryListWithPage(Map<String, Object> map) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("bizdatas", applicationDao.queryListWithPage(map));
		retMap.put("total", applicationDao.count(map));
		return retMap;
	}

	@Override
	@Transactional
	public Integer save(Application application) throws Exception {
		String token = request.getHeader("Token");
		UserObject userObject = (UserObject)redisUtil.get(token);
		if (application.getId() != null && application.getId() > 0) {
			application.setModifytime(new Date());
			application.setModifier(userObject.getEmployee().getEmpname());
			return applicationDao.update(application);
		} else {
			application.setDelflag("0");
			application.setCreatetime(new Date());
			application.setCreator(userObject.getEmployee().getEmpname());
			return applicationDao.insert(application);
		}
	}

	@Override
	@Transactional
	public Integer del(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			return applicationDao.deleteBatch(idArr);
		}
		return 0;
	}
	
}
