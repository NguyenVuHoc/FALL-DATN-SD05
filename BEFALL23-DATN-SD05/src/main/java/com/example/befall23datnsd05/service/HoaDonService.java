package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;

import java.time.LocalDate;
import java.util.List;

public interface HoaDonService {

    List<HoaDon> getAll();

    List<HoaDon> getByTrangThai(TrangThai trangThai);

    HoaDon findById(Long id);

    List<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end, TrangThai trangThai);

    boolean validate(HoaDon hoaDon, TrangThai trangThai, String newGhiChu);


}
