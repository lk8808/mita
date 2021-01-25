package com.tr.mita.portal.controller;

import com.tr.mita.portal.model.Menu;
import com.tr.mita.portal.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenusByAppid")
	public List<Menu> queryMenusByAppid(String appid) {
		return menuService.queryMenusByAppid(appid);
	}
	
	@RequestMapping("/getMenuTreeByAppid")
	public List<Map<String, Object>> getMenuTreeByAppid(String appid) {
		return menuService.getMenuTreeByAppid(appid);
	}
	
	@RequestMapping("/queryMenusByParentid")
	public List<Menu> queryMenusByParentid(String parentid) {
		return menuService.queryMenusByParentid(parentid);
	}

	@RequestMapping("/getAuthAppMenuTree")
	public List<Map<String, Object>> getAuthAppMenuTree() {
		return menuService.getAuthAppMenuTree();
	}

	@RequestMapping("/getAppMenuTree")
	public List<Map<String, Object>> getAppMenuTree() {
		return menuService.getAppMenuTree();
	}
	
	@RequestMapping(value="/save")
	public Integer save(@RequestBody Menu menu) throws Exception {
		return menuService.save(menu);
	}
	
	@RequestMapping(value="/del")
	public Integer del(String ids) {
		return menuService.del(ids);
	}
	

	
}
