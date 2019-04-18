package com.tr.ibs.portal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.ibs.entity.RespData;
import com.tr.ibs.portal.model.Menu;
import com.tr.ibs.portal.service.IMenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;
	
	@RequestMapping("/queryAllList")
	public List<Menu> queryAllList() {
		return menuService.queryAllList();
	}
	
	@RequestMapping("/queryMenusLevel1")
	public List<Menu> queryMenusLevel1() {
		return menuService.queryMenusLevel1();
	}
	
	@RequestMapping("/queryAuthMenusLevel1ByAppid")
	public List<Menu> queryAuthMenusLevel1ByAppid(String appid) {
		return menuService.queryAuthMenusLevel1ByAppid(appid);
	}
	
	@RequestMapping("/queryMenusByParentid")
	public List<Menu> queryMenusByParentid(String parentid) {
		return menuService.queryMenusByParentid(parentid);
	}
	
	@RequestMapping("/getMenuTree")
	public List<Map<String, Object>> getMenuTree(String parentid) {
		return menuService.getMenuTree(parentid);
	}
	
	@RequestMapping("/getAppMenuTree")
	public List<Map<String, Object>> getAppMenuTree() {
		return menuService.getAppMenuTree();
	}
	
	@RequestMapping("/queryMenus4Tree")
	public RespData queryMenus4Tree(@RequestBody Map<String, Object> map) {
		return menuService.queryMenus4Tree(map);
	}
	
	@RequestMapping(value="/save")
	public RespData save(Menu menu) {
		return menuService.save(menu);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return menuService.del(ids);
	}
}
