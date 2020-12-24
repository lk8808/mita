package com.tr.mita.portal.controller;

import com.tr.mita.entity.RespData;
import com.tr.mita.portal.model.Menu;
import com.tr.mita.portal.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	@RequestMapping("/queryMenusByAppid")
	public RespData queryMenusByAppid(String appid) {
		return menuService.queryMenusByAppid(appid);
	}
	
	@RequestMapping("/getMenuTreeByAppid")
	public RespData getMenuTreeByAppid(String appid) {
		return menuService.getMenuTreeByAppid(appid);
	}
	
	@RequestMapping("/queryMenusByParentid")
	public RespData queryMenusByParentid(String parentid) {
		return menuService.queryMenusByParentid(parentid);
	}
	
	@RequestMapping("/getAppMenuTree")
	public RespData getAppMenuTree() {
		return menuService.getAppMenuTree();
	}
	
	@RequestMapping(value="/save")
	public RespData save(@RequestBody Menu menu) {
		return menuService.save(menu);
	}
	
	@RequestMapping(value="/del")
	public RespData del(String ids) {
		return menuService.del(ids);
	}
	
	@RequestMapping("/queryAuthHomeMenus")
	public List<Menu> queryAuthHomeMenus() {
		return menuService.queryAuthHomeMenus();
	}
	
}
