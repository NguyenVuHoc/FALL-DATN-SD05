package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHangChiTiet;

import java.time.LocalDate;
import java.util.List;

public interface ThongKeService {

    Long doanhThu(LocalDate from, LocalDate to);

    Long soDonHuy(LocalDate from, LocalDate to);

    Long soSanPhamHoanTra(LocalDate from, LocalDate to);

    List<GioHangChiTiet> getAllSoSanPhamHoanTraHoanLaiKho(LocalDate from, LocalDate to);


    List<Object[]> soLuongLoaiHoaDon(LocalDate from, LocalDate to);

    List<Object[]> hoaDonChiTiet(LocalDate from, LocalDate to);

    List<Object[]> thongKeDoanhThu(LocalDate from, LocalDate to);


}
