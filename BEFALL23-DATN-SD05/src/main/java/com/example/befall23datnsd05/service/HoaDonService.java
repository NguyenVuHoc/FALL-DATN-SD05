package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonService {

    List<HoaDon> getAll();

    List<HoaDon> getAllByKhachHang(Long id);


    List<HoaDon> getByTrangThai(TrangThaiDonHang trangThai);

    List<HoaDon> getByTrangThaiAndKhachHang(TrangThaiDonHang trangThai,Long id);

    HoaDon findById(Long id);

    HoaDon save(HoaDon hoaDon);

    List<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end);

    boolean validate(HoaDon hoaDon, TrangThaiDonHang trangThai, String newGhiChu);


    HoaDon createHdHoanTra(HoaDon hoaDon,Long idHd);

    boolean removeGioHangChiTietHoanTra(GioHangChiTiet gioHangChiTiet,HoaDon hoaDon);

    GioHangChiTiet createGioHangHoanTraByHoaDon(GioHangChiTiet gioHangChiTiet,HoaDon hoaDon);

    ChiTietSanPham refund(GioHangChiTiet gioHangChiTiet);




}
