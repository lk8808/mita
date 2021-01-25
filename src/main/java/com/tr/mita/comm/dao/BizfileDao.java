package com.tr.mita.comm.dao;

import com.tr.mita.comm.model.Bizfile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BizfileDao extends IBaseDao<Bizfile> {

    public List<Bizfile> queryByEntity(Map<String, Object> params);

}
