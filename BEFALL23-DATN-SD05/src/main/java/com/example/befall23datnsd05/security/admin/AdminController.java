package com.example.befall23datnsd05.security.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/")
    public String getFormLoginAdmin(){
        return "admin-template/signin";
    }

}
