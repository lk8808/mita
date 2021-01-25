package com.tr.mita.org.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tr.mita.base.exception.RespException;
import com.tr.mita.org.dao.PositionDao;
import com.tr.mita.org.model.Position;
import com.tr.mita.org.service.IPositionService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.mita.base.entity.UserObject;

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

	@Override
	public Map<String, Object> queryListWithPage(Map<String, Object> params) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		//设置分页
		int page = (int)params.get("page");
		int limit = (int)params.get("limit");
		params.put("begin", (page-1)*limit);
		retMap.put("bizdatas", positionDao.queryListWithPage(params));
		retMap.put("total", positionDao.count(params));
		return retMap;
	}

	@Override
	public List<Position> queryAllList() {
		return positionDao.queryAllList();
	}

	@Override
	public Integer save(Position position) throws Exception {
		String token = request.getHeader("Token");
		Position tmp = new Position();
		tmp.setId(position.getId());
		tmp.setPosno(position.getPosno());
		if (!isUnique(tmp)) {
			throw new RespException("200001", "岗位编号不唯一！");
		}
		UserObject userObject = (UserObject)redisUtil.get(token);
		if (position.getId() != null && position.getId() > 0) {
			position.setModifier(userObject.getUser().getUsername());
			position.setModifytime(new Date());
			return positionDao.update(position);
		} else {
			position.setDelflag("0");
			position.setCreator(userObject.getUser().getUsername());
			position.setCreatetime(new Date());
			return positionDao.insert(position);
		}
	}

	@Override
	public Integer del(String ids) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			return positionDao.deleteBatch(idArr);
		}
		return 0;
	}

	private boolean isUnique(Position position) {
		Long tmpId = position.getId();
		position.setId(null);
		Position tmp = positionDao.expand(position);
		if (tmp != null && tmpId != tmp.getId()) {
			return false;
		}
		return true;
	}

}
