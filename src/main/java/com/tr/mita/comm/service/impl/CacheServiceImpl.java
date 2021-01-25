package com.tr.mita.comm.service.impl;

import com.tr.mita.comm.service.ICacheService;
import com.tr.mita.base.entity.RespData;
import com.tr.mita.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Integer getInlineNum() {
        return redisUtil.getKeysPattern("token_*").size();
    }

    @Override
    public Set getKeys(String pattern) {
        return redisUtil.getKeysPattern(pattern);
    }

    @Override
    public Object getCache(String key) {
        return redisUtil.get(key);
    }

    @Override
    public RespData removeCache(String pattern) {
        RespData respData = new RespData();
        redisUtil.removePattern(pattern);
        return respData;
    }
}
