package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.wrapper.GioHangWrapper;

import java.math.BigDecimal;
import java.util.List;

public interface BanHangCustomerService {

    void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong);

    void xoaKhoiGioHang(Long id);

    void datHang(List<GioHangChiTiet> listGioHangChiTiet,String ten,String diaChi, String sdt, String ghiChu);

    void datHangItems(GioHangWrapper gioHangWrapper, String ten, String diaChi, String sdt, String ghiChu, BigDecimal shippingFee, BigDecimal tongTien, BigDecimal totalAmount, Long selectedVoucherId, BigDecimal diemTichLuy, String useAll);

    List<GioHangChiTiet> updateGioHangChiTiet(Long idGioHangChiTiet, Integer soLuong);

    List<GioHangChiTiet> findAllById(List<String> listIdString);

    GioHangWrapper findAllItemsById(List<String> listIdString);

    Long getIdHoaDonVuaMua(Long idKhachHang);

}
