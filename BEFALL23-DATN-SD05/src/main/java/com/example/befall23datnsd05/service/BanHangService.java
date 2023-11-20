package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;

import java.math.BigDecimal;
import java.util.List;

public interface BanHangService {

    List<HoaDon> getHoaDonCho();

    List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(Long idHoaDon);

    HoaDon getOneById(Long idHoaDon);

    ChiTietSanPham getChiTietSanPhamById(Long idChiTietSanPham);

    List<ChiTietSanPham> getChiTietSanPham();

    HoaDon themHoaDon(HoaDon hoaDon);

    HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham,Long idHoaDon, HoaDonChiTiet hoaDonChiTiet);

    HoaDonChiTiet getOneByIdHDCT(Long idHDCT);

    HoaDonChiTiet xoaHoaDonChiTiet(Long idHoaDonChiTiet);

    HoaDon thanhToanHoaDon(Long idHoaDon, String thanhTien);

    BigDecimal getTongTien(Long idHoaDon);


    ChiTietSanPham updateSoLuong(Long idSanPham, Integer soLuong);

    ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT);

    HoaDon updateKhachHang(Long idHoaDon, Long idKhachHang);

    HoaDonChiTiet tangSoLuongSanPham(Long idHDCT, Integer soLuong);

    HoaDonChiTiet tangSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    HoaDonChiTiet giamSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    ChiTietSanPham suaSoLuongSanPham(Long idHDCT);

    Boolean huyDon(Long idHoaDon);

    KhachHang tichDiem(Long idKhachHang, String thanhTien);
}
