package com.example.befall23datnsd05.security.admin;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.security.customer.CustomKhachHangDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomNhanVienDetailService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NhanVien> nhanVien = nhanVienRepository.findByEmail(username);
        Optional<KhachHang> khachHang = khachHangRepository.findByEmailAndIdNot(username, 1L);

        if (khachHang.isPresent()) {
            return new CustomKhachHangDetail(khachHang.get());
        } else if (nhanVien.isPresent()) {
            return new CustomNhanVienDetail(nhanVien.get());
        } else {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + username);
        }
    }
}
