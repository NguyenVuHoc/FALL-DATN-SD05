package com.example.befall23datnsd05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/thong-ke")
public class ThongKeController {

    @GetMapping
    public String hienThi(){
        return "admin-template/thong_ke/thong_ke";
    }
}
