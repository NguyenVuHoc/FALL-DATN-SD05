package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wingman")
public class CustomerController {
    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping("/doi-ngu")
    public String getFormDoiNgu(Model model){
        Long id = principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        model.addAttribute("checkSecurity",checkSecurity);
        return "customer-template/about";
    }
    @GetMapping("/dich-vu")
    public String getFormDichVu(Model model){
        Long id = principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        model.addAttribute("checkSecurity",checkSecurity);
        return "customer-template/services";
    }
    @GetMapping("/thong-tin")
    public String getFormThongTin(Model model){
        Long id = principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        model.addAttribute("checkSecurity",checkSecurity);
        return "customer-template/blog";
    }
    @GetMapping("/lien-he")
    public String getFormLienHe(Model model){
        Long id = principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        model.addAttribute("checkSecurity",checkSecurity);
        return "customer-template/contact";
    }
}
