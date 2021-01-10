package com.tr.mita.portal.dao;

import java.util.List;

import com.tr.mita.comm.dao.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.portal.model.Roleauthcfg;

@Mapper
public interface RoleauthcfgDao extends IBaseDao<Roleauthcfg> {

	public List<Roleauthcfg> queryByRoleid(Integer roleid);
	
	public Integer insertBatch(List<Roleauthcfg> roleauthcfgs);
	
	public Integer delByRoleid(Integer roleid);
	
}
