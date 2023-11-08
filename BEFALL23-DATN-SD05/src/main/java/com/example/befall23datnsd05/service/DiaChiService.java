package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.entity.DiaChi;

import java.util.List;

public interface DiaChiService {

    List<DiaChi> getAll();
    DiaChi getById(Long id);

    DiaChi add(DiaChiRequest diaChiRequest, Long idKhachHang);
    DiaChi update(DiaChiRequest diaChiRequest);
    void remove(Long id);
}
