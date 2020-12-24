package com.tr.mita.org.service;

import java.util.List;
import java.util.Map;

import com.tr.mita.org.model.Depposlnk;
import org.springframework.web.bind.annotation.RequestBody;

import com.tr.mita.entity.RespData;

public interface IDepposlnkService {

	public RespData queryByDepid(Integer depid);

}
