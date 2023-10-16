package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.service.LoginAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginAdminService {

    @Autowired
    private NhanVienRepository nhanVienRepository;
    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public NhanVien getByEmail(String email) {
        Optional<NhanVien> optional = nhanVienRepository.findByEmail(email);
        return optional.orElse(null);
    }

    @Override
    public String checkPassword(NhanVien nhanVien, String password) {
        return null;
    }


}
