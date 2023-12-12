package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.service.*;
import com.example.befall23datnsd05.wrapper.GioHangWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String cart(Model model) {
        List<GioHangChiTiet> listGioHangChiTiet = gioHangChiTietService.getAll(Long.valueOf(5));
        model.addAttribute("listGioHangChiTiet", listGioHangChiTiet);
        return "customer-template/cart";
    }

    @PostMapping("/add/{id}")
    public String addCart(@PathVariable("id") Long idChiTietSanPham,
                          @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet,
                          @RequestParam("soLuong") Integer soLuong,
                          Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(Long.valueOf(5), idChiTietSanPham, soLuong);
        model.addAttribute("success", "Thêm thành công");
        return "redirect:/wingman/chi-tiet-san-pham/" + idChiTietSanPham + "?success";
    }

    @GetMapping("/addOne/{id}")
    public String addOne(@PathVariable("id") Long idChiTietSanPham,
                         @ModelAttribute("gioHangChiTiet") GioHangChiTiet gioHangChiTiet,
                         Model model) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(idChiTietSanPham);
        banHangCustomerService.themVaoGioHang(Long.valueOf(5), idChiTietSanPham, 1);
        model.addAttribute("success", "Thêm thành công");
        return "redirect:/wingman/chi-tiet-san-pham/" + idChiTietSanPham + "?success";
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
        KhachHang khachHang1 = khachHangService.getById(Long.valueOf(5));
        DiaChi diaChi = khachHangService.getDiaChiByIdKhachHang(Long.valueOf(5)).get(0);

        model.addAttribute("diaChi2", diaChi);
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang1.getId()));
        model.addAttribute("newDiaChi", new DiaChiRequest());

        String[] optionArray = options.split(",");
        List<String> listIdString = Arrays.asList(optionArray);
        gioHangWrapper = banHangCustomerService.findAllItemsById(listIdString);
        model.addAttribute("gioHangWrapper", gioHangWrapper);
        model.addAttribute("options", options);
        model.addAttribute("idKhachHang", Long.valueOf(5));
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

    @PostMapping("/dat-hang")
    public String datHang(
            @ModelAttribute("gioHangWrapper") GioHangWrapper gioHangWrapper,
            @RequestParam("diaChi") String diaChi,
            @RequestParam("wardName") String xa,
            @RequestParam("districtName") String huyen,
            @RequestParam("provinceName") String thanhPho,
            @RequestParam("sdt") String sdt,
            @RequestParam("ghiChu") String ghiChu,
            @RequestParam("ten") String ten,
            @RequestParam(name = "shippingFee") BigDecimal shippingFee,
            @RequestParam("tongTienHang") String tongTien,
            @RequestParam(name = "originAmount") BigDecimal totalAmount,
            @RequestParam(name = "voucherId", required = false, defaultValue = "0") Long selectedVoucherId,
            @RequestParam(name = "diemTichLuyApDung", required = false, defaultValue = "0") BigDecimal diemTichLuyApDung,
            @RequestParam(name = "xuTichDiem", required = false, defaultValue = "false") String useAllPointsHidden,
            @RequestParam(name = "origin") BigDecimal diemTichLuy) {
        String diaChiCuThe = diaChi + "," + xa + "," + huyen + "," + thanhPho;
        banHangCustomerService.datHangItems(gioHangWrapper, ten, diaChiCuThe, sdt, ghiChu, shippingFee, BigDecimal.valueOf(Double.valueOf(tongTien)), totalAmount, selectedVoucherId, diemTichLuyApDung, diemTichLuy, useAllPointsHidden);
        return "redirect:/wingman/cart/thankyou";
    }

    @GetMapping("/add-dia-chi/{idKhachHang}")
    public String suaDiaChi(@PathVariable("idKhachHang") String idKhachHang,
                            @ModelAttribute("newDiaChi") DiaChiRequest diaChiRequest,
                            @RequestParam("phuongXaID") String phuongXa,
                            @RequestParam("quanHuyenID") String quanHuyen,
                            @RequestParam("thanhPhoID") String thanhPho,
                            Model model) {
        KhachHang khachHang = khachHangService.getById(Long.valueOf(idKhachHang));
        model.addAttribute("diaChi2", khachHangService.getDiaChiByIdKhachHang(Long.valueOf(idKhachHang)).get(0));
        model.addAttribute("diaChi", khachHangService.getDiaChiByIdKhachHang(khachHang.getId()));
        model.addAttribute("gioHangWrapper", gioHangWrapper);
        model.addAttribute("idKhachHang", 5L);
        BigDecimal diemTichLuy = khachHang.getTichDiem();
        model.addAttribute("diemTichLuy", diemTichLuy);
        long total = 0;
        for (GioHangChiTiet gh : gioHangWrapper.getListGioHangChiTiet()) {
            total += (long) gh.getDonGia().intValue() * gh.getSoLuong();
        }
        List<MaGiamGia> vouchers = maGiamGiaService.layList(total);
        model.addAttribute("vouchers", vouchers);
        diaChiService.add(diaChiRequest, Long.valueOf(idKhachHang), thanhPho, quanHuyen, phuongXa);
        return "customer-template/checkout";
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
