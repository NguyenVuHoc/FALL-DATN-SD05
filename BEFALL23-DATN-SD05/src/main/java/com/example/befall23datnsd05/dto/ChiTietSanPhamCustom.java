package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.entity.*;

import java.math.BigDecimal;

public interface ChiTietSanPhamCustom {

     Long getId();

     SanPham getSanPham();

     MauSac getMauSac();

     KichThuoc getKichThuoc();

     Integer getSoLuongTon();

     BigDecimal getGiaBan();

     Integer getTrangThai();

}
