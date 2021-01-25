package com.tr.mita.comm.controller;

import com.tr.mita.comm.model.Fileclass;
import com.tr.mita.comm.service.IFileclassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fileclass")
public class FileclassController {

    @Autowired
    private IFileclassService fileclassService;

    @RequestMapping(value="/queryList")
    public Map<String, Object> queryList(@RequestBody Map<String, Object> map) {
        return fileclassService.queryListWithPage(map);
    }

    @RequestMapping(value="/save")
    public Integer save(@RequestBody Fileclass fileclass) throws Exception {
        return fileclassService.save(fileclass);
    }

    @RequestMapping(value="/del")
    public Integer del(String ids) {
        return fileclassService.del(ids);
    }

}
