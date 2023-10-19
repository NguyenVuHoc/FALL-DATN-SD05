package com.example.befall23datnsd05.mapper;

import com.example.befall23datnsd05.dto.KhachHangDangKyDto;
import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.security.crypto.password.PasswordEncoder;

public class KhachHangMapper {

//    static PasswordEncoder passwordEncoder;
    public static KhachHang convertKhachHangDangKyDtoToKhachHang(KhachHangDangKyDto dto) {
        KhachHang khachHang = new KhachHang();
        khachHang.setEmail(dto.getEmail());
        khachHang.setTen(dto.getTen());
        khachHang.setMatKhau(dto.getMatKhau());
        khachHang.setSdt(dto.getSdt());
        khachHang.setGioiTinh(dto.getGioiTinh());
        return khachHang;
    }
}
