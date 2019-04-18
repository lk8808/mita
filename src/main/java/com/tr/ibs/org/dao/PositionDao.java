package com.tr.ibs.org.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tr.ibs.base.IBaseDao;
import com.tr.ibs.org.model.Position;

@Mapper
public interface PositionDao extends IBaseDao<Position> {

	public int deleteBatch(String[] ids);
}
