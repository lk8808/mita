package com.tr.mita.portal.service;

import com.tr.mita.portal.model.Rolereslnk;

import java.util.List;
import java.util.Map;

public interface IRolereslnkService {

    public List<Rolereslnk> queryByRoleid(Integer roleid);

    public Integer saveBath(Map<String, Object> params);

}
