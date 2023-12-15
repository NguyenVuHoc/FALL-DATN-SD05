package com.example.befall23datnsd05.security.admin;

import com.example.befall23datnsd05.dto.RegisterRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.sendEmail.SendMailService;
import com.example.befall23datnsd05.service.KhachHangService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @Autowired
    private KhachHangService khachHangService;
    @Autowired
    private SendMailService sendMailService;
    @GetMapping("/login")
    public String getFormLoginAdmin(Model model) {
        model.addAttribute("khachHang", new RegisterRequest());
        model.addAttribute("validateRegis", false);
        return "admin-template/login";
    }

    @PostMapping("/dang-ky")
    public String dangKy(
            @Valid
            @ModelAttribute("khachHang") RegisterRequest khachHang,
            BindingResult result,
            Model model
            ){
        if(result.hasErrors() || khachHangService.existsByEmail(khachHang.getEmail()) || khachHangService.existsBySdt(khachHang.getSdt())){
            model.addAttribute("validateRegis", true);
            model.addAttribute("exSdt", khachHangService.existsBySdt(khachHang.getSdt()));
            model.addAttribute("exEmail", khachHangService.existsByEmail(khachHang.getEmail()));
            return "admin-template/login";
        }
        khachHangService.registration(khachHang);
        return "redirect:/login";
    }
    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_ADMIN")) {
            return "redirect:/admin/ban-hang";
        }
        return "redirect:/wingman/trang-chu";

    }

    @PostMapping("/quen-mat-khau")
    public String quenMatKhau(@RequestParam("mail")String mail,Model model){
        if(!khachHangService.existsByEmail(mail)){
            model.addAttribute("vld", true);
            model.addAttribute("vld1", "Email bạn nhập không tồn tại hoặc chưa đăng ký!");
            model.addAttribute("exEmail1", khachHangService.existsByEmail(mail));
            return "admin-template/login";
        }else {
            khachHangService.quenMatKhau(mail);
            sendMailService.sendNewPassWord(mail);
            return "admin-template/login";
        }
    }
}
