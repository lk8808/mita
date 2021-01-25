package com.tr.mita.fims.controller;

import com.tr.mita.fims.model.Shop;
import com.tr.mita.fims.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    private IShopService shopService;

    @RequestMapping("/queryList")
    public Map<String, Object> queryList(@RequestBody Map<String, Object> params) {
        return shopService.queryListWithPage(params);
    }

    @RequestMapping(value="/save")
    public Integer save(@RequestBody Shop shop) throws Exception {
        return shopService.save(shop);
    }

    @RequestMapping(value="/del")
    public Integer del(String ids) {
        return shopService.del(ids);
    }

}
