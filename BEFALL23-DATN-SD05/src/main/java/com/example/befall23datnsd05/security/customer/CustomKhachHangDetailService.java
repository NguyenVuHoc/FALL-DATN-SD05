package com.example.befall23datnsd05.security.customer;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomKhachHangDetailService implements UserDetailsService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KhachHang khachHang = khachHangRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("Invalid"));
        return new CustomKhachHangDetail(khachHang);
    }
}
