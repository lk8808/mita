package com.tr.mita.portal.dao;

import java.util.List;
import java.util.Map;

import com.tr.mita.base.IBaseDao;
import com.tr.mita.portal.model.Menu;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuDao extends IBaseDao<Menu> {

	public List<Menu> queryMenusLevel1();
	
	public List<Menu> queryAuthMenusLevel1ByAppid(String appid);
	
	public List<Menu> queryMenusByParentid(String parentid);
	
	public List<Menu> queryMenusByAppid(String appid);
	
	public List<Menu> querySubMenusByParentidWithPage(Map<String, Object> params);
	
	public Integer countSubMenusByParentid(Map<String, Object> params);
	
	public List<Menu> querySubMenusByAppidWithPage(Map<String, Object> params);
	
	public Integer countSubMenusByAppid(Map<String, Object> params);
	
	public int deleteBatch(String[] ids);
	
	public List<Menu> queryAuthHomeMenus(String userid);
}
