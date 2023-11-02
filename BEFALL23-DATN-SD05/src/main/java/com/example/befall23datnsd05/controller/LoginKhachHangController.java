package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.KhachHangDangKyDto;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginKhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @GetMapping("/login")
    public String login() {
        return "customer-template/dangnhap";
    }

    @GetMapping("/dang-ky")
    public String getFormDangKy(Model model) {
        model.addAttribute("khachHang", new KhachHangDangKyDto());
        return "customer-template/dangky";
    }

    @PostMapping("/dang-ky")
    public String dangKy(
            @ModelAttribute("khachHang") KhachHangDangKyDto khachHangDangKyDto
    ) {
        khachHangService.save(khachHangDangKyDto);
        return "redirect:/khach-hang/dang-ky";
    }
}
