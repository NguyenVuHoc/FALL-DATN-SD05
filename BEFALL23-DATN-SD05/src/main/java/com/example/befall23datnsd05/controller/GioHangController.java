package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.service.BanHangCustomerService;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @Autowired
    private KhachHangService khachHangService;

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

    @GetMapping("/xoa-sp/{id}")
    public String xoaSanPhamCheckOut(@PathVariable("id") Long id){
        banHangCustomerService.xoaKhoiGioHang(id);
        return "redirect:/wingman/cart/checkout";
    }

    @GetMapping("/checkout")
    public String checkout(Model model,
                           @ModelAttribute("khachHang") KhachHang khachHang,
                           @RequestParam String options){
        KhachHang khachHang1 = khachHangService.getById(Long.valueOf(5));
        DiaChi diaChi = khachHangService.getDiaChiByIdKhachHang(Long.valueOf(5)).get(0);
        model.addAttribute("diaChi2", diaChi);
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
        String[] optionArray = options.split(",");
        List<String> listIdString = Arrays.asList(optionArray);
        List<GioHangChiTiet> listGioHangChiTiet = banHangCustomerService.findAllById(listIdString);
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        model.addAttribute("idKhachHang", Long.valueOf(5));
        return "customer-template/checkout";
    }

    @PostMapping("/dat-hang")
    public String datHang(
            @RequestParam("diaChi") String diaChi,
            @RequestParam("sdt") String sdt,
            @RequestParam("ghiChu") String ghiChu,
            @RequestParam("ten") String ten){
        banHangCustomerService.datHang(ten, diaChi, sdt, ghiChu);
        return "redirect:/wingman/cart/thankyou";
    }

    @GetMapping("/sua-dia-chi/{idDiaChi}")
    public String suaDiaChi(@PathVariable("idDiaChi") String idDiaChi,
                            Model model){
        model.addAttribute("diaChi2", khachHangService.getByIdDiaChi(Long.valueOf(idDiaChi)));
        KhachHang khachHang1 = khachHangService.getById(Long.valueOf(5));
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.getAll();
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        model.addAttribute("idKhachHang", Long.valueOf(5));
        return "customer-template/checkout";
    }

    @PostMapping("/mua-ngay/{id}")
    public String muaNgay(@PathVariable("id") Long idChiTietSanPham,
                          @RequestParam("soLuong") Integer soLuong,
                          Model model){
        banHangCustomerService.themVaoGioHang(Long.valueOf(1), idChiTietSanPham, soLuong);
        KhachHang khachHang1 = khachHangService.getById(Long.valueOf(5));
        DiaChi diaChi = khachHangService.getDiaChiByIdKhachHang(Long.valueOf(5)).get(0);
        model.addAttribute("diaChi2", diaChi);
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
        model.addAttribute("idKhachHang", Long.valueOf(5));
        return "redirect:/wingman/cart/checkout";
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<String> updateQuantity(@RequestParam Long idGioHangChiTiet, @RequestParam Integer soLuong) {
        try {
            // Update quantity in the database using your service
            banHangCustomerService.updateGioHangChiTiet(idGioHangChiTiet, soLuong);
            return ResponseEntity.ok("Update successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating quantity: " + e.getMessage());
        }
    }

    @GetMapping("/thankyou")
    public String b(){
        return "customer-template/thankyou";
    }
}
