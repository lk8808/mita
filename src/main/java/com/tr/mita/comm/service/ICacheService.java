package com.tr.mita.comm.service;

import com.tr.mita.base.entity.RespData;

import java.util.Set;

public interface ICacheService {

    public Integer getInlineNum();

    public Set getKeys(String pattern);

    public Object getCache(String key);

    public RespData removeCache(String pattern);
    
}
