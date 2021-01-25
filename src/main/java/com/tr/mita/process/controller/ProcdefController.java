package com.tr.mita.process.controller;

import com.tr.mita.process.service.ProcdefService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/procdef")
public class ProcdefController {

    @Autowired
    private ProcdefService procdefService;

    @RequestMapping(value="/deploy")
    public String deploy(@RequestBody Map<String, String> params) throws Exception {
        return procdefService.deploy(params);
    }

    @RequestMapping(value="/queryList")
    public Map<String, Object> queryListWithPage(@RequestBody Map<String, Object> params) {
        return procdefService.queryListWithPage(params);
    }

    @RequestMapping(value="/queryListByKey")
    public List<Map<String, Object>> queryListByKey(String key) {
        return procdefService.queryListByKey(key);
    }

    @RequestMapping(value="/getBpmn")
    public String getBpmn(String procdefid) {
        return procdefService.getBpmn(procdefid);
    }

    @RequestMapping(value="/del")
    public void del(String deploymentid) {
        procdefService.del(deploymentid);
    }

    @RequestMapping(value="/delByKey")
    public void delByKey(String key) {
        procdefService.delByKey(key);
    }

}
