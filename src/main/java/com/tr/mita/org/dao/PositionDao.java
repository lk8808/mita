package com.tr.mita.org.dao;

import org.apache.ibatis.annotations.Mapper;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.org.model.Position;

@Mapper
public interface PositionDao extends IBaseDao<Position> {
}
