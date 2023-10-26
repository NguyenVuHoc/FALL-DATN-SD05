package com.example.befall23datnsd05.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wingman")
public class CustomerController {

    @GetMapping("/doi-ngu")
    public String getFormDoiNgu(){
        return "customer-template/about";
    }
    @GetMapping("/dich-vu")
    public String getFormDichVu(){
        return "customer-template/services";
    }
    @GetMapping("/thong-tin")
    public String getFormThongTin(){
        return "customer-template/blog";
    }
    @GetMapping("/lien-he")
    public String getFormLienHe(){
        return "customer-template/contact";
    }
}
