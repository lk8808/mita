package com.tr.mita.portal.dao;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.dao.IBaseDao;
import com.tr.mita.portal.model.Homecfg;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomecfgDao extends IBaseDao<Homecfg> {

	public List<Map<String, Object>> queryMyhomecfg(String userid);
	
	public Integer insertBatch(List<Homecfg> homesetcfgs);
	
	public Integer delHomecfgByUserid(String userid);
	
}
