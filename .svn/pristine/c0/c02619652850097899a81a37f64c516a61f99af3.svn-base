package com.tr.ibs.portal.service.impl;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import com.tr.ibs.portal.dao.MenuDao;
import com.tr.ibs.portal.dao.RecordlogDao;
import com.tr.ibs.portal.model.Application;
import com.tr.ibs.portal.model.Menu;
import com.tr.ibs.portal.model.Recordlog;
import com.tr.ibs.portal.service.IRecordlogService;

@Service
@Transactional
public class RecordlogServiceImpl implements IRecordlogService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RecordlogDao recordlogDao;
	
	@Autowired
	private ApplicationDao applicationDao;
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest request;
	
	public RespData addRecordlog(String recordid,String recordtype){
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		try {
			Recordlog recordlog = new Recordlog();
			recordlog.setUserid(userObject.getUser().getId().longValue());
			recordlog.setRecordtype(recordtype);
			recordlog.setRecordid(Long.parseLong(recordid));
			recordlog.setRecount(1L);
			recordlog.setIpaddress(request.getRemoteAddr());
			recordlog.setModifytime(new Date());
			if("app".equals(recordtype)){
				Application application = this.applicationDao.get(Integer.parseInt(recordid));
				recordlog.setRecordname(application.getAppname());
				recordlog.setUrl(application.getUrl());
			}else if("menu".equals(recordtype)){
				Menu menu = this.menuDao.get(Integer.parseInt(recordid));
				recordlog.setRecordname(menu.getMenuname());
				recordlog.setUrl(menu.getMenuurl());
			}
			recordlogDao.insert(recordlog);
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	};
	
	@Override
	public RespData queryRecordlogByPage(Map<String, Object> map) {
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		int page = (int)map.get("page");
		int rows = (int)map.get("rows");
		map.put("begin", (page-1)*rows);
		map.put("userid", userObject.getUser().getId().toString());
		respData.setRtdata("rows", recordlogDao.queryListWithPage(map));
		respData.setRtdata("total", recordlogDao.count(map));
		
		return respData;
	}
	
	@Override
	public RespData queryRecordlogFetch10(Map<String, Object> map) {
		UserObject userObject = (UserObject)session.getAttribute("userObject");
		RespData respData = new RespData(new Rtsts("000000", "查询成功！"));
		map.put("begin", 0);
		map.put("rows", 5);
		map.put("userid", userObject.getUser().getId().toString());
		respData.setRtdata("rows", recordlogDao.queryListWithPage(map));
		return respData;
	}
	
	
}
