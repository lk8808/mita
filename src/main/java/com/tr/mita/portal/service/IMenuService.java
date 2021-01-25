package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.portal.model.Menu;

public interface IMenuService {
	
	public Menu get(Integer id);
	
	public List<Menu> queryAllList();

	public List<Menu> queryMenusByAppid(String appid);

	public List<Menu> queryMenusByParentid(String parentid);

	public List<Map<String, Object>> getMenuTreeByAppid(String appid);

	public List<Map<String, Object>> getAuthAppMenuTree();
	
	public List<Map<String, Object>>  getAppMenuTree();
	
	public Integer save(Menu menu) throws Exception;
	
	public Integer del(String ids);

}
