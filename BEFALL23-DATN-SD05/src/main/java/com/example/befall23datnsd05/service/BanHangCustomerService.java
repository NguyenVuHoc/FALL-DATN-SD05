package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.wrapper.GioHangWrapper;

import java.math.BigDecimal;
import java.util.List;

public interface BanHangCustomerService {

    GioHangChiTiet themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong);

    void xoaKhoiGioHang(Long id);

    void datHang(List<GioHangChiTiet> listGioHangChiTiet,String ten,String diaChi, String sdt, String ghiChu);

    HoaDon datHangItems(GioHangWrapper gioHangWrapper, Long idKachHang, String ten, String diaChi, String sdt, String ghiChu, BigDecimal shippingFee, BigDecimal tongTien, BigDecimal totalAmount, BigDecimal tienGiamGia, Long selectedVoucherId, BigDecimal diemTichLuy, String useAll);

    List<GioHangChiTiet> updateGioHangChiTiet(Long idGioHangChiTiet, Integer soLuong);

    List<GioHangChiTiet> findAllById(List<String> listIdString);

    GioHangWrapper findAllItemsById(List<String> listIdString);
}
