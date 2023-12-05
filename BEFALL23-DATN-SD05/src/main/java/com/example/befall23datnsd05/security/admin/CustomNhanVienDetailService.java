package com.example.befall23datnsd05.security.admin;

import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomNhanVienDetailService implements UserDetailsService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        NhanVien nhanVien = nhanVienRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + username));

        if (nhanVien == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        System.out.println(nhanVien.getEmail());
        return new CustomNhanVienDetail(nhanVien);
    }
}
