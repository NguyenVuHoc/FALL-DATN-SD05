package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.MaGiamGiaRequest;
import com.example.befall23datnsd05.entity.MaGiamGia;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;

import java.time.LocalDate;
import java.util.List;

public interface MaGiamGiaService {

    List<MaGiamGia> getAll();

    List<MaGiamGia> getListHoatDong();

    List<MaGiamGia> getByTrangThai(TrangThaiKhuyenMai trangThaiKhuyenMai);

    List<MaGiamGia> findMaGiamGia(LocalDate start, LocalDate end, TrangThaiKhuyenMai trangThaiKhuyenMai);

    void updateTrangThai();

    MaGiamGia add(MaGiamGiaRequest maGiamGiaRequest);

    MaGiamGia getById(Long id);

    MaGiamGia update(MaGiamGiaRequest maGiamGiaRequest);

    void huy(Long id);

    boolean existsByTen(String ten);
}
