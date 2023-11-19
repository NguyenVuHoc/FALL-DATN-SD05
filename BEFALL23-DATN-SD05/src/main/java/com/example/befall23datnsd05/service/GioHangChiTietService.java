package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.request.GioHangChiTietRequest;

import java.util.List;
import java.util.Optional;

public interface GioHangChiTietService {
    List<GioHangChiTiet> getAll();

    List<GioHangChiTiet> findGioHangChiTietById(Long id);

    Optional<GioHangChiTiet> getOne(Long id);

    GioHangChiTiet save(GioHangChiTiet gioHangChiTiet);

}
