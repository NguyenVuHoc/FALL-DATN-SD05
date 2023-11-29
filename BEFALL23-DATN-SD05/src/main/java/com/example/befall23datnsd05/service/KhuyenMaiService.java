package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.KhuyenMaiRequest;
import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;

import java.time.LocalDate;
import java.util.List;

public interface KhuyenMaiService {

    List<KhuyenMai> getList();

    void updateTrangThai();

    List<KhuyenMai> getByTrangThai(TrangThaiKhuyenMai trangThaiKhuyenMai);

    KhuyenMai add(KhuyenMaiRequest khuyenMaiRequest);

    KhuyenMai update(KhuyenMaiRequest khuyenMaiRequest);

    KhuyenMai getById(Long id);

    void huy(Long id);

    List<KhuyenMai> findKhuyenMai(LocalDate start, LocalDate end, TrangThaiKhuyenMai trangThaiKhuyenMai);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);
}
