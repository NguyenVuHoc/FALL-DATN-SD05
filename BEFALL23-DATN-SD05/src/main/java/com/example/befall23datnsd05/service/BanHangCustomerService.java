package com.example.befall23datnsd05.service;

public interface BanHangCustomerService {

    void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong);

    void xoaKhoiGioHang(Long id);
}
