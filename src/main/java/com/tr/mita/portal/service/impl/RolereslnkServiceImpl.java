package com.tr.mita.portal.service.impl;

import com.tr.mita.portal.dao.RolereslnkDao;
import com.tr.mita.portal.model.Rolereslnk;
import com.tr.mita.portal.service.IRolereslnkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RolereslnkServiceImpl implements IRolereslnkService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RolereslnkDao rolereslnkDao;

    @Override
    public List<Rolereslnk> queryByRoleid(Integer roleid) {
        return rolereslnkDao.queryByRoleid(roleid);
    }

    @Override
    @Transactional
    public Integer saveBath(Map<String, Object> params) {
        Integer roleid = (Integer)params.get("roleid");
        List<Rolereslnk> bizdatas = (List<Rolereslnk>)params.get("bizdatas");
        rolereslnkDao.delByRoleid(roleid);
        if (bizdatas != null && bizdatas.size() > 0) {
            return rolereslnkDao.insertBatch(bizdatas);
        }
        return 0;
    }
}
