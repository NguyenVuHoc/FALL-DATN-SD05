package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChiTietSanPhamService {


    List<ChiTietSanPham> getAll();
    ChiTietSanPham getById(Long id);

    ChiTietSanPham add(ChiTietSanPhamRequest chiTietSanPham);
    ChiTietSanPham update(ChiTietSanPhamRequest chiTietSanPham);
    void remove(Long id);

    List<ChiTietSanPham> getByTrangThai(TrangThai trangThai);
}
