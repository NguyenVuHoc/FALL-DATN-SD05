package com.example.befall23datnsd05.dto.hoadon;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface HoaDonCustom {

    Long getId();

    String getMaHoaDon();

    String getSdt();

    String getTenKhachHang();

    LocalDate getNgayTao();

    BigDecimal getThanhToan();




    Integer getTrangThai();


}
