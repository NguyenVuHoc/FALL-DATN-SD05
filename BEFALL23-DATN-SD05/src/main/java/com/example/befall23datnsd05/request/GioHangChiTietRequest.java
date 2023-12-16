package com.example.befall23datnsd05.request;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class GioHangChiTietRequest {
    private Long id;

    private ChiTietSanPham chiTietSanPham;

    private HoaDon hoaDon;

    private GioHang gioHang;

    private BigDecimal donGia;

    private Integer soLuong;

    private TrangThai trangThai;

    private String ghiChu;


}
