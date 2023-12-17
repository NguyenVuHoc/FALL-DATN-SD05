package com.example.befall23datnsd05.controller;


import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.service.*;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import com.example.befall23datnsd05.wrapper.GioHangWrapper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
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

    @Autowired
    private MaGiamGiaService maGiamGiaService;

    private GioHangWrapper gioHangWrapper;

    @Autowired
    private DiaChiService diaChiService;

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    void thongBao(RedirectAttributes redirectAttributes, String thongBao, int trangThai) {
        if (trangThai == 0) {
            redirectAttributes.addFlashAttribute("checkThongBao", "thatBai");
            redirectAttributes.addFlashAttribute("thongBao", thongBao);
        } else if (trangThai == 1) {
            redirectAttributes.addFlashAttribute("checkThongBao", "thanhCong");
            redirectAttributes.addFlashAttribute("thongBao", thongBao);
        } else {

            redirectAttributes.addFlashAttribute("checkThongBao", "canhBao");
            redirectAttributes.addFlashAttribute("thongBao", thongBao);
        }

    }

    @GetMapping
    public String cart(Model model) {
        Long idKhachHang = principalKhachHang.getCurrentUserId();
        if(idKhachHang==null){
            return "redirect:/login";
        }
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.getAll(idKhachHang);
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        return "customer-template/cart";
    }

    @PostMapping("/add/{id}")
    public String addCart(@PathVariable("id") Long idChiTietSanPham,
                          @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet,
                          @RequestParam("soLuong") Integer soLuong,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        Long idKhachHang = principalKhachHang.getCurrentUserId();
        if(idKhachHang==null){
            return "redirect:/login";
        }
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(idKhachHang, idChiTietSanPham, soLuong);
//        model.addAttribute("success", "Thêm thành công");
        thongBao(redirectAttributes, "Thêm thành công", 1);
        return "redirect:/wingman/chi-tiet-san-pham/" + idChiTietSanPham;
    }

    @GetMapping("/addOne/{id}")
    public String addOne(@PathVariable("id") Long idChiTietSanPham,
                         @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        Long idKhachHang = principalKhachHang.getCurrentUserId();
        if(idKhachHang==null){
            return "redirect:/login";
        }
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(idKhachHang, idChiTietSanPham, 1);
        thongBao(redirectAttributes, "Thêm thành công", 1);
//        thongBao(redirectAttributes, "Thành công", 1);
        return "redirect:/wingman/chi-tiet-san-pham/" + idChiTietSanPham;
    }


    @GetMapping("/xoa/{id}")
    public String xoaKhoiGio(@PathVariable("id") Long id) {
        banHangCustomerService.xoaKhoiGioHang(id);
        return "redirect:/wingman/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model,
                           @ModelAttribute("khachHang") KhachHang khachHang,
                           @RequestParam String options) {
        Long idKhachHang = principalKhachHang.getCurrentUserId();
        KhachHang khachHang1 = khachHangService.getById(idKhachHang);
        if (khachHangService.getDiaChiByIdKhachHang(idKhachHang).isEmpty()) {
            model.addAttribute("diaChi2", new DiaChi());
            model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
            model.addAttribute("newDiaChi", new DiaChiRequest());

            String[] optionArray = options.split(",");
            List<String> listIdString = Arrays.asList(optionArray);
            gioHangWrapper = banHangCustomerService.findAllItemsById(listIdString);
            model.addAttribute("gioHangWrapper", gioHangWrapper);
            model.addAttribute("options", options);
            model.addAttribute("idKhachHang", idKhachHang);
            BigDecimal diemTichLuy = khachHang1.getTichDiem();
            model.addAttribute("diemTichLuy", diemTichLuy);
            System.out.println(diemTichLuy);
            long total = 0;
            for (GioHangChiTiet gh : gioHangWrapper.getListGioHangChiTiet()) {
                total += (long) gh.getDonGia().intValue() * gh.getSoLuong();
            }
            List<MaGiamGia> vouchers = maGiamGiaService.layList(total);
            model.addAttribute("vouchers", vouchers);
            return "customer-template/checkout";
        }

        model.addAttribute("diaChi2", khachHangService.getDiaChiByIdKhachHang(idKhachHang).get(0));
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
        model.addAttribute("newDiaChi", new DiaChiRequest());

        String[] optionArray = options.split(",");
        List<String> listIdString = Arrays.asList(optionArray);
        gioHangWrapper = banHangCustomerService.findAllItemsById(listIdString);
        model.addAttribute("gioHangWrapper", gioHangWrapper);
        model.addAttribute("options", options);
        model.addAttribute("idKhachHang", idKhachHang);
        BigDecimal diemTichLuy = khachHang1.getTichDiem();
        model.addAttribute("diemTichLuy", diemTichLuy);
        System.out.println(diemTichLuy);
        long total = 0;
        for (GioHangChiTiet gh : gioHangWrapper.getListGioHangChiTiet()) {
            total += (long) gh.getDonGia().intValue() * gh.getSoLuong();
        }
        model.addAttribute("tongTienCheck", total);
        List<MaGiamGia> vouchers = maGiamGiaService.getListHoatDong();
        model.addAttribute("vouchers", vouchers);
        return "customer-template/checkout";
    }

    @PostMapping("/dat-hang")
    public String datHang(
            @ModelAttribute("gioHangWrapper") GioHangWrapper gioHangWrapper,
            @RequestParam("diaChi2") String diaChi,
            @RequestParam("xaPhuong") String xa,
            @RequestParam("quanHuyen") String huyen,
            @RequestParam("thanhPho") String thanhPho,
            @RequestParam("sdt2") String sdt,
            @RequestParam("ghiChu") String ghiChu,
            @RequestParam("ten2") String ten,
            @RequestParam(name = "shippingFee") BigDecimal shippingFee,
            @RequestParam("tongTienHang") String tongTien,
            @RequestParam(name = "originAmount") BigDecimal totalAmount,
            @RequestParam(name = "voucherId", required = false, defaultValue = "0") Long selectedVoucherId,
            @RequestParam(name = "xuTichDiem", required = false, defaultValue = "false") String useAllPointsHidden,
            @RequestParam(name = "origin") BigDecimal diemTichLuy,
            @RequestParam(name = "tienGiamGia") BigDecimal tienGiamGia,
            Model model) {
        Long idKhachHang = principalKhachHang.getCurrentUserId();
        String diaChiCuThe = diaChi + "," + xa + "," + huyen + "," + thanhPho;
        banHangCustomerService.datHangItems(gioHangWrapper, idKhachHang, ten, diaChiCuThe, sdt, ghiChu, shippingFee, BigDecimal.valueOf(Double.valueOf(tongTien)), totalAmount, tienGiamGia, selectedVoucherId, diemTichLuy, useAllPointsHidden);
        return "redirect:/wingman/cart/thankyou";
    }

    @PostMapping("/checkout/add-dia-chi/{idKhachHang}")
    public String suaDiaChi(@PathVariable("idKhachHang") String idKhachHang,
                            @ModelAttribute("newDiaChi") DiaChiRequest diaChiRequest,
                            @RequestParam("phuongXaID") String phuongXa,
                            @RequestParam("quanHuyenID") String quanHuyen,
                            @RequestParam("thanhPhoID") String thanhPho,
                            @RequestParam("options") String options,
                            Model model) {
        diaChiService.add(diaChiRequest, Long.valueOf(idKhachHang), thanhPho, quanHuyen, phuongXa);
        KhachHang khachHang = khachHangService.getById(Long.valueOf(idKhachHang));
        model.addAttribute("diaChi2", khachHangService.getDiaChiByIdKhachHang(Long.valueOf(idKhachHang)).get(0));
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang.getId()));
        model.addAttribute("gioHangWrapper", gioHangWrapper);
        model.addAttribute("idKhachHang", idKhachHang);
        BigDecimal diemTichLuy = khachHang.getTichDiem();
        model.addAttribute("diemTichLuy", diemTichLuy);
        long total = 0;
        for (GioHangChiTiet gh : gioHangWrapper.getListGioHangChiTiet()) {
            total += (long) gh.getDonGia().intValue() * gh.getSoLuong();
        }
        List<MaGiamGia> vouchers = maGiamGiaService.layList(total);
        model.addAttribute("vouchers", vouchers);
        return "redirect:/wingman/cart/checkout?options=" + options;
    }

    @PostMapping("/checkout/sua-dia-chi/{idKhachHang}")
    public String addDiaChi(@Valid
                            @ModelAttribute("newDiaChi") DiaChiRequest diaChiRequest,
                            @RequestParam("phuongXa") String phuongXa,
                            @RequestParam("quanHuyen") String quanHuyen,
                            @RequestParam("thanhPho") String thanhPho,
                            @RequestParam("options") String options
    ) {
        diaChiService.update(diaChiRequest, thanhPho, quanHuyen, phuongXa);
        return "redirect:/wingman/cart/checkout?options=" + options;
    }

    @PostMapping("/updateQuantity")
    @ResponseBody
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
    public String b() {
        return "customer-template/thankyou";
    }
}
