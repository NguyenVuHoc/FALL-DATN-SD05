package com.example.befall23datnsd05.security.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class LoginAdminController {

    @GetMapping
    public String getFormLogin(){
        return "/admin-template/signin";
    }
}
