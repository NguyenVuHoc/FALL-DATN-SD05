package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.service.LoginAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.befall23datnsd05.enumeration.TrangThai.DANG_HOAT_DONG;

@Service
public class LoginServiceImpl implements LoginAdminService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public List<NhanVien> getByTrangThai(Integer trangThai) {
        return nhanVienRepository.findByTrangThai(trangThai);
    }

    @Override
    public String checkTrangThaiDangNhap(NhanVien nhanVien) {
        return nhanVien.getTrangThai()!= DANG_HOAT_DONG?"Nhân viên đã dừng hoạt động":"";
    }

    @Override
    public NhanVien getByEmail(String email) {
        Optional<NhanVien> optional = nhanVienRepository.findByEmail(email);
        return optional.orElse(null);
    }

    @Override
    public String checkPassword(NhanVien nhanVien, String password) {
        return nhanVien.getMatKhau().equals(password) ? "Đăng nhập thành công" : "Sai mật khẩu";
    }


}
