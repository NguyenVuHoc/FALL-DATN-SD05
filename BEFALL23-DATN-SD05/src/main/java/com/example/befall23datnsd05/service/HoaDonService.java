package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonService {

    List<HoaDon> getAll();

    List<HoaDon> getAllByKhachHang(Long id);


    List<HoaDon> getByTrangThai(TrangThaiDonHang trangThai);

    List<HoaDon> getByTrangThaiAndKhachHang(TrangThaiDonHang trangThai,Long id);

    HoaDon findById(Long id);

    HoaDon save(HoaDon hoaDon);

    List<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end, TrangThaiDonHang trangThai);

    boolean validate(HoaDon hoaDon, TrangThaiDonHang trangThai, String newGhiChu);


}
