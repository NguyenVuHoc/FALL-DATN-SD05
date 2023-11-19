package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ChiTietSanPhamCustomerService {

    Page<ChiTietSanPham> pageAllInShop(Integer pageNo, Integer size);

    Integer nextPage(Integer pageNo);

    List<ChiTietSanPhamCustomerCustom> list3New();

    List<ChiTietSanPhamCustomerCustom> list3Prominent();

    List<ChiTietSanPhamCustomerCustom> list3Custom();

    List<ChiTietSanPhamCustomerCustom> list3Limited();

    List<ChiTietSanPhamCustomerCustom> list4Random();

    List<AnhCustomerCustom> listAnhDetail(Long id);

    ChiTietSanPham getById(Long id);
}
