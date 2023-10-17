package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.BanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {

    @Autowired
    private BanHangService banHangService;


    @GetMapping()
    public String hienThiBanHang(Model model){
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("listSanPham", banHangService.getChiTietSanPham());
        model.addAttribute("hoaDon", new HoaDon());
        return "/admin-template/ban_hang/ban_hang";
    }

    @GetMapping("/detail/{idHoaDon}")
    public String hienThiHoaDonChiTiet(@PathVariable("idHoaDon") String idHoaDon, Model model){
        List<HoaDonChiTiet> listHoaDonChiTiet = banHangService.getHoaDonChiTietByIdHoaDon(Long.valueOf(idHoaDon));
//        HoaDon hoaDon = banHangService.getByMa(maHoaDon);
        model.addAttribute("listHoaDonChiTiet", listHoaDonChiTiet);
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("listSanPham",  banHangService.getChiTietSanPham());
//        model.addAttribute("hoaDonDetail", hoaDon);
        return "admin-template/ban_hang/ban_hang";
    }

    @PostMapping("/tao-hoa-don")
    public String taoHoaDon(@ModelAttribute("hoaDon") HoaDon hoaDon){
        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(Long.valueOf(4));
        KhachHang khachHang = new KhachHang();
        khachHang.setId(Long.valueOf(5));
        hoaDon = HoaDon.builder()
                .ma(maHD)
                .nhanVien(nhanVien)
                .khachHang(khachHang)
                .ngayTao(LocalDate.now())
                .trangThai(TrangThai.HOA_DON_CHO)
                .build();
        banHangService.themHoaDon(hoaDon);
        return "redirect:/admin/ban-hang";
    }

}
