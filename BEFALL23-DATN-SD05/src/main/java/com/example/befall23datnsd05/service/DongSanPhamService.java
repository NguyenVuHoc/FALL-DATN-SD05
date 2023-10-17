package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.DongSanPham;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DongSanPhamService {


    List<DongSanPham> getList();

    Page<DongSanPham> getAll(Integer pageNo, Integer size);

    void save(DongSanPham dongSanPham);
    void  update(DongSanPham dongSanPham);

    void remove(Long id);

    DongSanPham findById(Long id);



}
