package com.example.befall23datnsd05.security.admin;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin/login")
    public String getFormLoginAdmin(){
        return "admin-template/signin";
    }

}
