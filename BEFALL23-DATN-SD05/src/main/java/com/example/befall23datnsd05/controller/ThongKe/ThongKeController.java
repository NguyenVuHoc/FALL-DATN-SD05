package com.example.befall23datnsd05.controller.ThongKe;

import com.example.befall23datnsd05.service.ThongKeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/thong-ke")
public class ThongKeController {
private final ThongKeService thongKeService;

    public ThongKeController(ThongKeService thongKeService) {
        this.thongKeService = thongKeService;
    }

    @GetMapping
    public String hienThi(Model model){
        model.addAttribute("doanhThu",thongKeService.doanhThu());
        model.addAttribute("soDonHuy",thongKeService.soDonHuy());
        model.addAttribute("hoanTra",thongKeService.soSanPhamHoanTra());
        return "admin-template/thong_ke/thong_ke";
    }
}
