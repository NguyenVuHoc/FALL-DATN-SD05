package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.service.LoginAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginAdminController {

    @Autowired
    private LoginAdminService loginAdminService;

    @GetMapping()
    public String getFormLogin(){
        return "admin-template/signin";
    }
}
