package com.example.befall23datnsd05.custom;


import java.math.BigDecimal;
import java.time.LocalDate;

public interface HoaDonCustom {
    Long getId();

    String getMa();

    String getSdt();

    String getTen();

    LocalDate getNgayTao();

    BigDecimal getThanhToan();

    BigDecimal getTongTien();

    String getDiaChi();

    String getGhiChu();


    Integer getTrangThai();
}
