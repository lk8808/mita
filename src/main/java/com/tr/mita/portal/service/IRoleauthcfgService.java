package com.tr.mita.portal.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.comm.entity.RespData;
import com.tr.mita.portal.model.Roleauthcfg;

public interface IRoleauthcfgService {

	public List<Roleauthcfg> queryByRoleid(Integer roleid);

	public Integer saveBath(Map<String, Object> params);
}
