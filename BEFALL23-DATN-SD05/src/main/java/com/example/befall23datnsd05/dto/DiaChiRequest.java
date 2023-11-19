package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.enumeration.TrangThai;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DiaChiRequest {

    private Long id;

    private String tenNguoiNhan;

    private String diaChi;

    private String sdt;

    private String ghiChu;

    private TrangThai trangThai;

    private Long khachHang;
}
