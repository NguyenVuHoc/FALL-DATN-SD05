package com.example.befall23datnsd05.controller.banhang;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.service.BanHangService;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@RestController
public class BanHangRestContrller {

    @Autowired
    private BanHangService banHangService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    HttpServletRequest request;

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

    @RequestMapping(value = "/admin/ban-hang/check-thanh-toan", method = {RequestMethod.GET, RequestMethod.POST})
    public Integer checkThanhToan(@RequestParam("id") String idHoaDonCho) {
        if (idHoaDonCho.equals("")) {
            return 1;
        } else if (banHangService.getHoaDonChiTietByIdHoaDon(Long.valueOf(idHoaDonCho)).isEmpty()) {
            return 2;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/admin/ban-hang/check-huy-don", method = {RequestMethod.GET, RequestMethod.POST})
    public Integer checkHuyDon(@RequestParam("id") String idHoaDonCho) {
        if (idHoaDonCho.equals("")) {
            return 1;
        } else {
            return 0;
        }
    }

    @RequestMapping(value = "/admin/ban-hang/them-san-pham/check-so-luong/{soLuongTon}", method = {RequestMethod.GET, RequestMethod.POST})
    public Integer checkSoLuong(@RequestParam("soLuong") String soLuong,
                                @PathVariable("soLuongTon") Integer soLuongTon) {
        try {
            if (soLuong.equals("")) {
                return 1;
            } else if (Integer.parseInt(soLuong) <= 0) {
                return 2;
            } else if (Integer.parseInt(soLuong) > soLuongTon) {
                return 3;
            } else {
                return 0;
            }
        } catch (NumberFormatException numberFormatException) {
            return 4;
        }
    }

    @RequestMapping(value = "/admin/ban-hang/them-san-pham/check-giam-so-luong/{soLuongSanPham}", method = {RequestMethod.GET, RequestMethod.POST})
    public Integer checkGiamSoLuong(@RequestParam("soLuong") String soLuong,
                                    @PathVariable("soLuongSanPham") Integer soLuongSanPham) {
        try {
            if (soLuong.equals("")) {
                return 1;
            } else if (Integer.parseInt(soLuong) <= 0) {
                return 2;
            } else if (Integer.parseInt(soLuong) > soLuongSanPham) {
                return 3;
            } else {
                return 0;
            }
        } catch (NumberFormatException numberFormatException) {
            return 4;
        }
    }

    @RequestMapping(value = "/admin/ban-hang/check-them-voucher", method = {RequestMethod.GET, RequestMethod.POST})
    public Integer checkVoucher(@RequestParam("idHoaDon") String idHoaDon,
                                @RequestParam("idMaGiamGia") String idMaGiamGia,
                                @RequestParam("tongTien") String tongTien,
                                RedirectAttributes redirectAttributes) {
        Integer checkVoucher = banHangService.checkVoucher(Long.valueOf(idHoaDon), Long.valueOf(idMaGiamGia), BigDecimal.valueOf(Double.valueOf(tongTien)));
        if (checkVoucher == 1) {
            return 2;
        } else if (checkVoucher == 2) {
            return 3;
        } else {
            return 0;
        }
    }

}
