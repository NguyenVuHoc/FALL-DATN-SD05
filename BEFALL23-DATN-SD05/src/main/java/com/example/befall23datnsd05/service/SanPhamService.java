package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.request.SanPhamRequest;

import java.util.List;

public interface SanPhamService {


    List<SanPham> getList();

    List<SanPhamCustom> getAll();

    List<SanPhamCustom> getByTrangThai(TrangThai trangThai);

    SanPham save(SanPhamRequest request);

    SanPham update(SanPhamRequest request);

    void remove(Long id);

    SanPham findById(Long id);

    boolean existByMa(String ma);


    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);

}
