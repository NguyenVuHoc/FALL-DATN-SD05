package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginKhachHangService extends UserDetailsService {

    KhachHang create(KhachHang khachHang);
}
