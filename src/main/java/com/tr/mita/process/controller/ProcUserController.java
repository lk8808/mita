package com.tr.mita.process.controller;

import com.tr.mita.process.service.ProcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procuser")
public class ProcUserController {

    @Autowired
    private ProcUserService userService;

    @RequestMapping(value="/sync")
    public void syncUser() {
        userService.syncUser();
    }
}
