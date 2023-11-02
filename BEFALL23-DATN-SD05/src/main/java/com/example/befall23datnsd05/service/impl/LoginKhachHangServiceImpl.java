package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.LoginKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LoginKhachHangServiceImpl implements LoginKhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public KhachHang create(KhachHang khachHang) {
        return khachHangRepository.save(khachHang);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        KhachHang khachHang =khachHangRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Invalid username or password"));
        return new User(khachHang.getEmail(), khachHang.getMatKhau(), getAuthorities());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<String> roles = null;
        return roles.stream().map(
                role -> new SimpleGrantedAuthority("KHACH_HANG")
        ).collect(Collectors.toList());
    }
}
