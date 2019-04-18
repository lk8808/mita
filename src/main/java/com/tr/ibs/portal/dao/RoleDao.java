package com.tr.ibs.portal.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.portal.model.Role;

@Mapper
public interface RoleDao extends IBaseDao<Role> {
	
	public List<Role> queryRolesByAppid(Integer appid);
	
	public List<Role> queryRolesByAppidWithPage(Map<String, Object> params);
	
	public Integer countByAppid(Map<String, Object> params);

	public int deleteBatch(String[] ids);
}
