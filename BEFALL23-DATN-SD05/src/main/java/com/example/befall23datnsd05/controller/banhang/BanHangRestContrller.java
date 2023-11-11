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

@RestController
public class BanHangRestContrller {

    @Autowired
    private BanHangService banHangService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = "/admin/ban-hang/check-thanh-toan", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkThanhToan(@RequestParam("id") String idHoaDonCho) {
        if (idHoaDonCho.equals("")) {
            return "HoaDonNull";
        } else if (banHangService.getHoaDonChiTietByIdHoaDon(Long.valueOf(idHoaDonCho)).isEmpty()) {
            return "hoaDonChiTietNull";
        } else {
            return "OK";
        }
    }

    @RequestMapping(value = "/admin/ban-hang/them-san-pham/check-so-luong/{soLuongTon}", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkSoLuong(@RequestParam("soLuong") String soLuong,
                               @PathVariable("soLuongTon") Integer soLuongTon) {
        try {
            if (soLuong.equals("")) {
                return "soLuongNull";
            } else if (Integer.parseInt(soLuong) <= 0) {
                return "soLuongLonHonKhong";
            } else if (Integer.parseInt(soLuong) > soLuongTon) {
                return "lonHonSoLuongTon";
            } else {
                return "OK";
            }
        } catch (NumberFormatException numberFormatException) {
            return "soLuongPhaiLaSo";
        }
    }

}
