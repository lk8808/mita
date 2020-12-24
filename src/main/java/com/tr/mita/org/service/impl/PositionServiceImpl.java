package com.tr.mita.org.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tr.mita.org.dao.PositionDao;
import com.tr.mita.org.model.Employee;
import com.tr.mita.org.model.Position;
import com.tr.mita.org.service.IPositionService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.mita.entity.RespData;
import com.tr.mita.entity.Rtsts;
import com.tr.mita.entity.UserObject;

@Service
@Transactional
public class PositionServiceImpl implements IPositionService {
	
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public Position get(Integer id) {
		return positionDao.get(id);
	}

	@Override
	public RespData queryListWithPage(Map<String, Object> params) {
		RespData respData = new RespData();
		//设置分页
		int page = (int)params.get("page");
		int limit = (int)params.get("limit");
		params.put("begin", (page-1)*limit);
		respData.setRtdata("bizdatas", positionDao.queryListWithPage(params));
		respData.setRtdata("total", positionDao.count(params));
		return respData;
	}

	@Override
	public RespData queryAllList() {
		RespData respData = new RespData();
		respData.setRtdata("bizdatas", positionDao.queryAllList());
		return respData;
	}

	@Override
	public RespData save(Position position) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		String token = request.getHeader("Token");
		try {
			Position tmp = new Position();
			tmp.setId(position.getId());
			tmp.setPosno(position.getPosno());
			if (!isUnique(tmp)) {
				respData.setRtsts(new Rtsts("200001", "岗位编号不唯一！"));
				return respData;
			}
			UserObject userObject = (UserObject)redisUtil.get(token);
			if (position.getId() != null && position.getId() > 0) {
				position.setModifier(userObject.getUser().getUsername());
				position.setModifytime(new Date());
				positionDao.update(position);
			} else {
				position.setDelflag("0");
				position.setCreator(userObject.getUser().getUsername());
				position.setCreatetime(new Date());
				positionDao.insert(position);
			}
		} catch (Exception e) {
			e.printStackTrace();
			respData.setRtsts(new Rtsts("200001", "保存失败！"));
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
				positionDao.deleteBatch(idArr);
			}
		} catch (Exception e) {
			respData.setRtsts(new Rtsts("200002", "删除失败！"));
			logger.error(e.getMessage());
		}	
		return respData;
	}

	private boolean isUnique(Position position) {
		Integer tmpId = position.getId();
		position.setId(null);
		Position tmp = positionDao.expand(position);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

}
