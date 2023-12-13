package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.hoadonchitiet.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.MaGiamGia;
import org.springframework.data.domain.Page;

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

    HoaDon thanhToanHoaDon(Long idHoaDon);
    HoaDon checkXuHoaDon(Long idHoaDon, String tongTien, String thanhTien, Boolean xuTichDiem);

    BigDecimal voucher(Long idHoaDon, BigDecimal tongTien);

    Integer checkVoucher(Long idHoaDon, Long idMaGiamGia, BigDecimal tongTien);

    BigDecimal getTongTien(Long idHoaDon);

    BigDecimal getThanhTien(Long idHoaDon, BigDecimal tongTien);

    ChiTietSanPham updateSoLuong(Long idSanPham, Integer soLuong);

    ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT);

    HoaDon updateKhachHang(Long idHoaDon, Long idKhachHang);

    HoaDon themGiamGia(Long idHoaDon, Long idGiamGia, BigDecimal tongTien);

    HoaDon huyGiamGia(Long idHoaDon);

    MaGiamGia updateGiamGia(Long idHoaDon);

    HoaDon checkGiamGia(Long idHoaDon, BigDecimal tongTien);

    HoaDonChiTiet tangSoLuongSanPham(Long idHDCT, Integer soLuong);

    HoaDonChiTiet tangSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    HoaDonChiTiet giamSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    ChiTietSanPham suaSoLuongSanPham(Long idHDCT);

    Boolean huyDon(Long idHoaDon);

    KhachHang tichDiem(Long idKhachHang, String thanhTien);
}
