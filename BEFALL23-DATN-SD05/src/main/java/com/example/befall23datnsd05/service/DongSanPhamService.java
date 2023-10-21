package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.custom.DongSanphamCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.request.DongSanPhamRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DongSanPhamService {


    List<DongSanPham> getList();

    Page<DongSanphamCustom> getPage(Integer pageNo, Integer size);

    DongSanPham save(DongSanPhamRequest request);

    DongSanPham update(DongSanPhamRequest request);

    void remove(Long id);

    DongSanPham findById(Long id);

    Integer tranferPage(Integer pageNo);


}
