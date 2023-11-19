package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHangChiTiet;

import java.util.List;

public interface BanHangCustomerService {

    void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong);

    void xoaKhoiGioHang(Long id);

    void datHang(List<GioHangChiTiet> listGioHangChiTiet,String ten,String diaChi, String sdt, String ghiChu);

    List<GioHangChiTiet> updateGioHangChiTiet(Long idGioHangChiTiet, Integer soLuong);

    List<GioHangChiTiet> findAllById(List<String> listIdString);

}
