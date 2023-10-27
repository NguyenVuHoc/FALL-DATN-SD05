package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.entity.*;

import java.math.BigDecimal;

public interface ChiTietSanPhamCustom {

     SanPham getSanPham();

     DeGiay getDeGiay();

     MauSac getMauSac();

     KichThuoc getKichThuoc();

     Integer getSoLuongTon();

     BigDecimal getGiaMacDinh();

     BigDecimal getGiaBan();

     Integer getTrangThai();

}
