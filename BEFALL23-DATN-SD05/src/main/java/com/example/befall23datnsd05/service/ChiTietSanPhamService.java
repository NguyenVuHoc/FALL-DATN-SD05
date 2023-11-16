package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;

import java.util.List;

public interface ChiTietSanPhamService {


    List<ChiTietSanPham> getAll();
    ChiTietSanPham getById(Long id);

    ChiTietSanPham add(ChiTietSanPhamRequest chiTietSanPham);
    ChiTietSanPham update(ChiTietSanPhamRequest chiTietSanPham);
    void remove(Long id);

    List<ChiTietSanPham> getByTrangThai(TrangThai trangThai);

    //ctsp dùng bên khuyến mại
    List<ChiTietSanPham> getAllSanPhamKhuyenMai();
    List<ChiTietSanPham> getSpCoKhuyenMai(Long idKM);

    void updateIdKhuyenMai(Long idKM, Long idCtsp);

    void deleteIdKhuyenMai(Long idCtsp);

}
