package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.dto.hoadon.HoaDonCustom;
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

    HoaDonCustom getHoaDonById(Long idHoaDon);

    HoaDon getOneById(Long idHoaDon);

    ChiTietSanPham getChiTietSanPhamById(Long idChiTietSanPham);

    List<ChiTietSanPham> getChiTietSanPham();

    HoaDon themHoaDon(HoaDon hoaDon);

    HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham,Long idHoaDon, HoaDonChiTiet hoaDonChiTiet);

    HoaDonChiTiet getOneByIdHDCT(Long idHDCT);

    HoaDonChiTiet xoaHoaDonChiTiet(Long idHoaDonChiTiet);

    List<HoaDonChiTietCustom> getOneHDCTByHD(Long idHoaDon);

    HoaDon thanhToanHoaDon(Long idHoaDon, String tongTien, String thanhTien, Boolean xuTichDiem);

    BigDecimal getTongTien(Long idHoaDon);

    BigDecimal getThanhTien(Long idHoaDon, BigDecimal tongTien);

    Page<HoaDonChiTiet> getPhanTrang(Long idHoaDon, Integer pageNo, Integer size);

    Integer checkPageHDCT(Long idHoaDon, Integer pageNo);

    ChiTietSanPham updateSoLuong(Long idSanPham, Integer soLuong);

    ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT);

    List<KhachHang> getAllKhachHang();

    HoaDon updateKhachHang(Long idHoaDon, Long idKhachHang);

    HoaDon themGiamGia(Long idHoaDon, Long idGiamGia, BigDecimal tongTien);

    HoaDon huyGiamGia(Long idHoaDon);

    MaGiamGia updateGiamGia(Long idHoaDon);

    HoaDon checkGiamGia(Long idHoaDon, BigDecimal tongTien);

    HoaDonChiTiet tangSoLuongSanPham(Long idHDCT, Integer soLuong);

    HoaDonChiTiet tangSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    HoaDonChiTiet giamSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong);

    ChiTietSanPham suaSoLuongSanPham(Long idHDCT);

    Boolean checkThanhToan(Long idHoaDon);

    HoaDon save(HoaDon hoaDon);

    List<ChiTietSanPhamCustom> getSanPham();

    List<ChiTietSanPham> getSanPhamByMaAndTen(String maSanPham, String tenSanPham);

    List<ChiTietSanPham> getSanPhamByMaAndTenAndMauAndSize(String maSanPham, String tenSanPham, String mauSac, String kichThuoc);

    Boolean huyDon(Long idHoaDon);

    KhachHang tichDiem(Long idKhachHang, String thanhTien);
}
