package com.example.befall23datnsd05.security.admin;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomNhanVienDetail implements UserDetails {

    private final NhanVien nhanVien;

    public CustomNhanVienDetail(NhanVien nhanVien){
        this.nhanVien = nhanVien;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public Long getId(){
        return  nhanVien.getId();
    }

    public String getTen(){
        return  nhanVien.getTen();
    }

    @Override
    public String getPassword() {
        return nhanVien.getMatKhau();
    }

    @Override
    public String getUsername() {
        return nhanVien.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
