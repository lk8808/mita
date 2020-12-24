package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Menu;

public interface IMenuService {
	
	public Menu get(Integer id);
	
	public List<Menu> queryAllList();

	public RespData queryMenusByAppid(String appid);

	public RespData queryMenusByParentid(String parentid);

	public RespData getMenuTreeByAppid(String appid);
	
	public RespData getAppMenuTree();
	
	public RespData save(Menu menu);
	
	public RespData del(String ids);
	
	public List<Menu> queryAuthHomeMenus();
}
