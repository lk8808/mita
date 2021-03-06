package com.tr.mita.portal.service.impl;

import com.tr.mita.base.entity.RespData;
import com.tr.mita.portal.dao.RoleauthcfgDao;
import com.tr.mita.portal.model.Roleauthcfg;
import com.tr.mita.portal.service.IRoleauthcfgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoleauthcfgServiceImpl implements IRoleauthcfgService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleauthcfgDao roleauthcfgDao;

	@Override
	public List<Roleauthcfg> queryByRoleid(Integer roleid) {
		return roleauthcfgDao.queryByRoleid(roleid);
	}

	@Override
	@Transactional
	public Integer saveBath(Map<String, Object> params) {
		RespData respData = new RespData();
		Integer roleid = (Integer)params.get("roleid");
		List<Roleauthcfg> bizdatas = (List<Roleauthcfg>)params.get("bizdatas");
		roleauthcfgDao.delByRoleid(roleid);
		if (bizdatas != null && bizdatas.size() > 0) {
			return roleauthcfgDao.insertBatch(bizdatas);
		}
		return 0;
	}
}
