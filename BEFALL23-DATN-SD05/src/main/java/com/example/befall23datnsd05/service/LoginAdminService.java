package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.NhanVien;

import java.util.List;
import java.util.Optional;

public interface LoginAdminService {

    List<NhanVien> getAll();

    List<NhanVien> getByTrangThai(Integer trangThai);
    String checkTrangThaiDangNhap(NhanVien nhanVien);

    NhanVien getByEmail(String email);

    String checkPassword(NhanVien nhanVien, String password);

}
