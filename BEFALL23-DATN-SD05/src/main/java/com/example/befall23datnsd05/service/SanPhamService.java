package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.request.SanPhamRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SanPhamService {


    List<SanPham> getList();

    Page<SanPhamCustom> getPage(Integer pageNo, Integer size);

    SanPham save(SanPhamRequest request);

    SanPham update(SanPhamRequest request);

    void remove(Long id);

    SanPham findById(Long id);

    Integer transferPage(Integer pageNo);




}
