package com.tr.mita.portal.dao;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.dao.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.portal.model.Role;

@Mapper
public interface RoleDao extends IBaseDao<Role> {
	
	public List<Role> queryRolesByAppid(Integer appid);
	
	public List<Role> queryRolesByAppidWithPage(Map<String, Object> params);
	
	public Integer countByAppid(Map<String, Object> params);

}
