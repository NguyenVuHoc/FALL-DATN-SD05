package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;

import java.util.List;

public interface HoaDonChiTietService {

    List<HoaDonChiTiet> getCtspById(Long id);
}
