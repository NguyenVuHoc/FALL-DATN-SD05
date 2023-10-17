package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.enumeration.GioiTinh;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangDangKyDto {
    private String ten;
    private String email;
    private String matKhau;
    private String diaChi;
    private String sdt;
    private GioiTinh gioiTinh;

}
