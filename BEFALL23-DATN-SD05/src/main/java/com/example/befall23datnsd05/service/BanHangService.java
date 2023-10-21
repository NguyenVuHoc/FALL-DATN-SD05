package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.hoadon.HoaDonCustom;
import com.example.befall23datnsd05.dto.hoadon.HoaDonRequest;
import com.example.befall23datnsd05.dto.hoadonchitiet.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface BanHangService {

    List<HoaDon> getHoaDonCho();

    List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(Long idHoaDon);

    HoaDonCustom getHoaDonById(Long idHoaDon);

    HoaDon getOneById(Long idHoaDon);

    ChiTietSanPham getChiTietSanPhamById(Long idChiTietSanPham);

    List<ChiTietSanPham> getChiTietSanPham();

    HoaDon themHoaDon(HoaDon hoaDon);

    HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham,Long idHoaDon, HoaDonChiTiet hoaDonChiTiet);

    HoaDonChiTiet xoaHoaDonChiTiet(Long idHoaDonChiTiet);

    List<HoaDonChiTietCustom> getOneHDCTByHD(Long idHoaDon);

    HoaDon thanhToanHoaDon(HoaDonRequest hoaDonRequest);

    BigDecimal getTongTien(Long idHoaDon);

    Page<HoaDonChiTiet> getPhanTrang(Long idHoaDon, Integer pageNo, Integer size);

    Integer checkPageHDCT(Long idHoaDon, Integer pageNo);

    ChiTietSanPham updateSoLuong(Long idSanPham);

    ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT);

}
