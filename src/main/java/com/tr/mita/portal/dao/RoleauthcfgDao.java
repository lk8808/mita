package com.tr.mita.portal.dao;

import java.util.List;
import java.util.Map;

import com.tr.mita.base.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.portal.model.Roleauthcfg;

@Mapper
public interface RoleauthcfgDao extends IBaseDao<Roleauthcfg> {

	public List<Map<String, Object>> queryRoleauthcfgsByRoleid(Integer roleid);
	
	public Integer insertBatch(List<Roleauthcfg> roleauthcfgs);
	
	public Integer delRoleauthcfgByRoleid(Integer roleid);
	
}
