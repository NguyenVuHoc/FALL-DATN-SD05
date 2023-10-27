package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface KhachHangService {

    Page<KhachHang> phanTrang(Integer pageNo, Integer size);

    KhachHang add(KhachHangRequest khachHangRequest);

    KhachHang update(KhachHangRequest khachHangRequest);

    void remove(Long id);

    KhachHang getById(Long id);

    boolean exist(String ma);

    Integer chuyenPage(Integer pageNo);

    Page<KhachHang> timTen(String ten,Integer pageNo, Integer size);

    Page<KhachHang> getTrangThaiHoatDong(Integer pageNo, Integer size);

    Page<KhachHang> getTrangThaiDungHoatDong(Integer pageNo, Integer size);
}
