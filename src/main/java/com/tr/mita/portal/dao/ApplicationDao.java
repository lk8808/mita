package com.tr.mita.portal.dao;

import java.util.List;

import com.tr.mita.base.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.portal.model.Application;

@Mapper
public interface ApplicationDao extends IBaseDao<Application> {

	public int deleteBatch(String[] ids);
	
	List<Application> queryAllAuthList(String userid);
	
}
