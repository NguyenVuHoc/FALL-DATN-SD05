package com.example.befall23datnsd05.dto.hoadon;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class HoaDonRequest {

    private String id;

    private String maHoaDon;

    private String sdt;
    private String tenKhachHang;

    private String thanhToan;

    public HoaDon map(HoaDon hoaDon) {
        hoaDon.setId(Long.valueOf(this.getId()));
        hoaDon.setMa(this.getMaHoaDon());
        hoaDon.setNgaySua(LocalDate.now());
        hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(this.getThanhToan())));
        hoaDon.setKhachHang(KhachHang.builder().ten(this.tenKhachHang).build());
        return hoaDon;
    }
}
