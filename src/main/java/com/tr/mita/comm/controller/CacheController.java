package com.tr.mita.comm.controller;

import com.tr.mita.comm.service.ICacheService;
import com.tr.mita.comm.entity.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private ICacheService cacheService;

    @RequestMapping("/getInlineNum")
    public Integer getInlineNum() {
        return cacheService.getInlineNum();
    }

    @RequestMapping("/keys")
    public Set getKeys(String pattern) {
        return cacheService.getKeys(pattern);
    }

    @RequestMapping("/get")
    public Object getCache(String key) {
        return cacheService.getCache(key);
    }

    @RequestMapping("/remove")
    public RespData removeCache(String pattern) {
        return cacheService.removeCache(pattern);
    }
}
