package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChiTietSanPhamService {


    List<ChiTietSanPhamCustom> getAll();
    ChiTietSanPham getById(Long id);

    ChiTietSanPham add(ChiTietSanPhamRequest chiTietSanPhamRequest);
    ChiTietSanPham update(ChiTietSanPhamRequest chiTietSanPhamRequest);
    void remove(Long id);

    Page<ChiTietSanPhamCustom> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);

}
