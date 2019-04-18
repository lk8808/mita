package com.tr.ibs.portal.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;
import com.tr.ibs.entity.UserObject;
import com.tr.ibs.portal.dao.ApplicationDao;
import com.tr.ibs.portal.model.Application;
import com.tr.ibs.portal.service.IApplicationService;

@Service
@Transactional
public class ApplicationServiceImpl implements IApplicationService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public List<Application> queryAllList() {
		return applicationDao.queryAllList();
	}
	
	@Override
	public List<Application> queryAllAuthList() {
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		return applicationDao.queryAllAuthList(userObject.getUser().getId().toString());
	}
	
	@Override
	public RespData queryApplicationsWithPage(Map<String, Object> map) {
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		int page = (int)map.get("page");
		int rows = (int)map.get("rows");
		map.put("begin", (page-1)*rows);
		respData.setRtdata("rows", applicationDao.queryListWithPage(map));
		respData.setRtdata("total", applicationDao.count(map));
		
		return respData;
	}

	@Override
	public RespData save(Application application) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		try {
			if (application.getId() != null && application.getId() > 0) {
				applicationDao.update(application);
			} else {
				application.setDelflag("0");
				application.setRegeditdate(new Date());
				if (userObject != null && userObject.getUser() != null) {
					application.setPublisher(userObject.getUser().getUsername());
				}
				applicationDao.insert(application);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	@Override
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				applicationDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "删除失败！"));
			logger.error(e.getMessage());
		}	
		
		return respData;
	}
	
}
