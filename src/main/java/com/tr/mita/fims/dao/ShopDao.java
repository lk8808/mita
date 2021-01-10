package com.tr.mita.fims.dao;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.fims.model.Shop;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopDao extends IBaseDao<Shop> {

    public int deleteBatch(String[] ids);

}
