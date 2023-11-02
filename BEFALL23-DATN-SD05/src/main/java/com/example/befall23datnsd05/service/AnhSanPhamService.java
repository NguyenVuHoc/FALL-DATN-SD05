package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;

import java.util.List;

public interface AnhSanPhamService {
    AnhSanPham save(AnhSanPham anhSanPham);

    AnhSanPham update(AnhSanPham anhSanPham);

    List<AnhSanPham> getAnh(SanPham sanPham);

    void deleteByIdSp(Long id);

}
