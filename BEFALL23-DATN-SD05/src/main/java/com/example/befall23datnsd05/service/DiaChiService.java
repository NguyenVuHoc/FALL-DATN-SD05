package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.entity.DiaChi;

import java.util.List;

public interface DiaChiService {

    List<DiaChi> getAll();

    List<DiaChi> getAllTheoKhachHang(Long id);

    DiaChi getById(Long id);

    DiaChi add(DiaChiRequest diaChiRequest, Long idKhachHang, String thanhPho, String quanHuyen, String phuongXa);

    DiaChi update(DiaChiRequest diaChiRequest, Long id);

    void remove(Long id);

}
