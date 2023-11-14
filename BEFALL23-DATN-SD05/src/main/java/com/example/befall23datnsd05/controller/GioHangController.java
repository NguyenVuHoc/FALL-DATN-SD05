package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.service.BanHangCustomerService;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/wingman/cart")
public class GioHangController {

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

    @Autowired
    private BanHangCustomerService banHangCustomerService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @GetMapping
    public String cart(Model model){
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.getAll();
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        return "customer-template/cart";
    }

    @PostMapping("/add/{id}")
    public String addCart(@PathVariable("id") Long idChiTietSanPham,
                          @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet,
                          @RequestParam("soLuong") Integer soLuong){
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(Long.valueOf(1), idChiTietSanPham, soLuong);
        return "redirect:/wingman/cart";
    }

    @GetMapping("/addOne/{id}")
    public String addOne(@PathVariable("id") Long idChiTietSanPham,
                         @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet){
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(Long.valueOf(1), idChiTietSanPham, 1);
        return "redirect:/wingman/cart";
    }


    @GetMapping("/xoa/{id}")
    public String xoaKhoiGio(@PathVariable("id") Long id){
        banHangCustomerService.xoaKhoiGioHang(id);
        return "redirect:/wingman/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.getAll();
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        return "customer-template/checkout";
    }

    @GetMapping("/thankyou")
    public String b(){
        return "customer-template/thankyou";
    }
}
