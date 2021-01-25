package com.tr.mita.fims.service.impl;

import com.tr.mita.base.entity.UserObject;
import com.tr.mita.base.exception.RespException;
import com.tr.mita.fims.dao.ShopDao;
import com.tr.mita.fims.model.Shop;
import com.tr.mita.fims.service.IShopService;
import com.tr.mita.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShopServiceImpl implements IShopService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ShopDao shopDao;

    @Override
    public Map<String, Object> queryListWithPage(Map<String, Object> params) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", shopDao.count(params));
        retMap.put("bizdatas", shopDao.queryListWithPage(params));
        return retMap;
    }

    @Override
    @Transactional
    public Integer save(Shop shop) throws Exception{
        String token = request.getHeader("Token");
        Shop tmp = new Shop();
        tmp.setId(shop.getId());
        tmp.setShname(shop.getShname());
        if (!isUnique(tmp)) {
            throw new RespException("200001", "商户已存在！");
        }
        UserObject userObject = (UserObject)redisUtil.get(token);
        if (shop.getId() != null && shop.getId() > 0) {
            shop.setModifier(userObject.getUser().getUsername());
            shop.setModifytime(new Date());
            return shopDao.update(shop);
        } else {
            shop.setDelflag("0");
            shop.setCreator(userObject.getUser().getUsername());
            shop.setCreatetime(new Date());
            return shopDao.insert(shop);
        }
    }

    @Override
    public Integer del(String ids) {
        if (ids != null) {
            String[] idArr = ids.split(",");
            return shopDao.deleteBatch(idArr);
        }
        return 0;
    }

    private boolean isUnique(Shop shop) {
        Long tmpId = shop.getId();
        shop.setId(null);
        Shop tmp = shopDao.expand(shop);
        if (tmp != null && tmpId != tmp.getId()) {
            return false;
        }
        return true;
    }

}
