package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wingman")
public class CustomerController {
    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping("/doi-ngu")
    public String getFormDoiNgu(){
        Long id = principalKhachHang.getCurrentUserId();
        if (id == null) {
            return "redirect:/login";
        }
        return "customer-template/about";
    }
    @GetMapping("/dich-vu")
    public String getFormDichVu(){
        Long id = principalKhachHang.getCurrentUserId();
        if (id == null) {
            return "redirect:/login";
        }
        return "customer-template/services";
    }
    @GetMapping("/thong-tin")
    public String getFormThongTin(){
        Long id = principalKhachHang.getCurrentUserId();
        if (id == null) {
            return "redirect:/login";
        }
        return "customer-template/blog";
    }
    @GetMapping("/lien-he")
    public String getFormLienHe(){
        Long id = principalKhachHang.getCurrentUserId();
        if (id == null) {
            return "redirect:/login";
        }
        return "customer-template/contact";
    }
}
