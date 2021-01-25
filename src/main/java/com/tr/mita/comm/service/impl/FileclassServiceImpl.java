package com.tr.mita.comm.service.impl;

import com.tr.mita.base.entity.UserObject;
import com.tr.mita.base.exception.RespException;
import com.tr.mita.comm.dao.FileclassDao;
import com.tr.mita.comm.model.Fileclass;
import com.tr.mita.comm.service.IFileclassService;
import com.tr.mita.org.model.Position;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileclassServiceImpl implements IFileclassService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private FileclassDao fileclassDao;

    @Override
    public Map<String, Object> queryListWithPage(Map<String, Object> map) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("bizdatas", fileclassDao.queryListWithPage(map));
        retMap.put("total", fileclassDao.count(map));
        return retMap;
    }

    @Override
    public Integer save(Fileclass fileclass) throws Exception {
        String token = request.getHeader("Token");
        Fileclass tmp = new Fileclass();
        tmp.setId(fileclass.getId());
        tmp.setEntityname(fileclass.getEntityname());
        if (!isUnique(tmp)) {
            throw new RespException("200001", "文件分类不唯一！");
        }
        UserObject userObject = (UserObject)redisUtil.get(token);
        if (fileclass.getId() != null && fileclass.getId() > 0) {
            fileclass.setModifytime(new Date());
            fileclass.setModifier(userObject.getEmployee().getEmpname());
            return fileclassDao.update(fileclass);
        } else {
            fileclass.setDelflag("0");
            fileclass.setCreatetime(new Date());
            fileclass.setCreator(userObject.getEmployee().getEmpname());
            return fileclassDao.insert(fileclass);
        }
    }

    @Override
    public Integer del(String ids) {
        if (ids != null) {
            String[] idArr = ids.split(",");
            return fileclassDao.deleteBatch(idArr);
        }
        return 0;
    }

    private boolean isUnique(Fileclass fileclass) {
        Long tmpId = fileclass.getId();
        fileclass.setId(null);
        Fileclass tmp = fileclassDao.expand(fileclass);
        if (tmp != null && tmpId != tmp.getId()) {
            return false;
        }
        return true;
    }
}
