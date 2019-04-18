package com.tr.ibs.portal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.portal.model.Application;

@Mapper
public interface ApplicationDao extends IBaseDao<Application> {

	public int deleteBatch(String[] ids);
	
	List<Application> queryAllAuthList(String userid);
	
}
