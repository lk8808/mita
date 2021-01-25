package com.tr.mita.portal.dao;

import java.util.List;

import com.tr.mita.comm.dao.IBaseDao;
import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.portal.model.Application;

@Mapper
public interface ApplicationDao extends IBaseDao<Application> {
	
	List<Application> queryAllAuthList(String userid);
	
}
