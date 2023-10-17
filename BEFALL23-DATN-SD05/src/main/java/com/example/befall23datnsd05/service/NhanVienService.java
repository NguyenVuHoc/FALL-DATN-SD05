package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.NhanVien;

import java.util.List;

public interface NhanVienService {

    List<NhanVien> getAll();

    Boolean add(NhanVien nhanVien);

    Boolean update(NhanVien nhanVien);

    void remove(Long id);

    NhanVien getById(Long id);
}
