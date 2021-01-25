package com.tr.mita.comm.service;

import com.tr.mita.comm.model.Fileclass;

import java.util.Map;

public interface IFileclassService {

    public Map<String, Object> queryListWithPage(Map<String, Object> map);

    public Integer save(Fileclass fileclass) throws Exception;

    public Integer del(String ids);

}
