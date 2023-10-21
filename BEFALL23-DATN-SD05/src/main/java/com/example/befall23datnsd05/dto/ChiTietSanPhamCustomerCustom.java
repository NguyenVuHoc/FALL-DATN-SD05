package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.entity.AnhSanPham;

import java.math.BigDecimal;
import java.util.List;

public interface ChiTietSanPhamCustomerCustom {

    String getTen();

    Long getId();

    BigDecimal getGiaBan();

    List<AnhSanPham> getListAnhSanPham();
}
