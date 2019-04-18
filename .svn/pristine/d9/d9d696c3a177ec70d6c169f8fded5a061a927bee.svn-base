package com.tr.ibs.portal.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.entity.Rtsts;
import com.tr.ibs.portal.dao.RoleauthcfgDao;
import com.tr.ibs.portal.model.Roleauthcfg;
import com.tr.ibs.portal.service.IRoleauthcfgService;

@Service
@Transactional
public class RoleauthcfgServiceImpl implements IRoleauthcfgService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleauthcfgDao roleauthcfgDao;

	@Override
	public List<Map<String, Object>> queryRoleauthcfgsByRoleid(Integer roleid) {
		return roleauthcfgDao.queryRoleauthcfgsByRoleid(roleid);
	}

	@Override
	public RespData saveRoleauthcfgByRole(Map<String, Object> params) {
		RespData respData = new RespData(new Rtsts("000000", "保存成功！"));
		try {
			Integer roleid = (Integer)params.get("roleid");
			List<Map<String, Object>> list = (ArrayList<Map<String, Object>>)params.get("roleauthcfgs");
			List<Roleauthcfg> roleauthcfgs = new ArrayList<Roleauthcfg>();
			for (Map<String, Object> map : list) {
				Roleauthcfg roleauthcfg = new Roleauthcfg();
				Integer authcfgid = (String)map.get("authcfgid") == null || "".equals((String)map.get("authcfgid"))
						? null : Integer.valueOf((String)map.get("authcfgid"));
				Integer departmentid = (String)map.get("departmentid") == null || "".equals((String)map.get("departmentid"))
						? null : Integer.valueOf((String)map.get("departmentid"));
				Integer positionid = (String)map.get("positionid") == null || "".equals((String)map.get("positionid"))
						? null : Integer.valueOf((String)map.get("positionid"));
				roleauthcfg.setCfgtype((String)map.get("cfgtype"));
				roleauthcfg.setAuthcfgid(authcfgid);
				roleauthcfg.setDepartmentid(departmentid);
				roleauthcfg.setPositionid(positionid);
				roleauthcfg.setRoleid(roleid);
				roleauthcfgs.add(roleauthcfg);
			}
			//删除旧关联
			roleauthcfgDao.delRoleauthcfgByRoleid(roleid);
			//新增
			roleauthcfgDao.insertBatch(roleauthcfgs);
		} catch (Exception e) {
			e.printStackTrace();
			respData.setRtsts(new Rtsts("100001", "保存失败！"));
			logger.error(e.getMessage());
		}
		return respData;
	}

}
