package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHangChiTiet;

import java.util.List;

public interface GioHangChiTietService {

    List<GioHangChiTiet> getAll(Long idKhachHang);
}
