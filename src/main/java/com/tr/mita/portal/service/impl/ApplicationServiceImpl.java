package com.tr.mita.portal.service.impl;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;
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
	public RespData queryAllList() {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", applicationDao.queryAllList());
		return respData;
	}
	
	@Override
	public RespData queryAllAuthList() {
		RespData respData = new RespData();
		String token = request.getHeader("Token");
		if (token != null) {
			UserObject userObject = (UserObject)redisUtil.get(token);
			respData.setRtdata("bizdatas", applicationDao.queryAllAuthList(userObject.getUser().getId().toString()));
			return respData;
		}
		return respData;
	}
	
	@Override
	public RespData queryListWithPage(Map<String, Object> map) {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", applicationDao.queryListWithPage(map));
		respData.setRtdata("total", applicationDao.count(map));
		return respData;
	}

	@Override
	@Transactional
	public RespData save(Application application) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		String token = request.getHeader("Token");
		try {
			UserObject userObject = (UserObject)redisUtil.get(token);
			if (application.getId() != null && application.getId() > 0) {
				application.setModifytime(new Date());
				application.setModifier(userObject.getEmployee().getEmpname());
				applicationDao.update(application);
			} else {
				application.setDelflag("0");
				application.setCreatetime(new Date());
				application.setCreator(userObject.getEmployee().getEmpname());
				applicationDao.insert(application);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	@Override
	@Transactional
	public RespData del(String ids) {
		RespData respData = new RespData(new Rtsts("000000", "删除成功！"));
		try {
			if (ids != null) {
				String[] idArr = ids.split(",");
				applicationDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		
		return respData;
	}
	
}
