package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVien> getAll() {
        return nhanVienRepository.findAll();
    }

    @Override
    public Boolean add(NhanVien nhanVien) {
        NhanVien nhanVien1 = new NhanVien();
        nhanVien1.setMa(nhanVien.getMa());
        nhanVien1.setTen(nhanVien.getTen());
        nhanVien1.setSdt(nhanVien.getSdt());
        nhanVien1.setGioiTinh(nhanVien.getGioiTinh());
        nhanVien1.setNgayTao(LocalDate.now());
        nhanVien1.setNgaySua(LocalDate.now());
        nhanVien1.setEmail(nhanVien.getEmail());
        nhanVien1.setMatKhau(nhanVien.getMatKhau());
        nhanVien1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        nhanVienRepository.save(nhanVien1);
        return true;
    }

    @Override
    public Boolean update(NhanVien nhanVien) {
        if (nhanVien == null) {
            return false;
        }
        NhanVien existingNhanVien = nhanVienRepository.getReferenceById(nhanVien.getId());
        if (existingNhanVien == null) {
            return false;
        }
        existingNhanVien.setTen(nhanVien.getTen());
        existingNhanVien.setSdt(nhanVien.getSdt());
        existingNhanVien.setGioiTinh(nhanVien.getGioiTinh());
        existingNhanVien.setNgaySua(LocalDate.now());
        existingNhanVien.setEmail(nhanVien.getEmail());
        existingNhanVien.setMatKhau(nhanVien.getMatKhau());
        existingNhanVien.setTrangThai(TrangThai.DANG_HOAT_DONG);

        try {
            nhanVienRepository.save(existingNhanVien);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void remove(Long id) {
        nhanVienRepository.deleteById(id);
    }

    @Override
    public NhanVien getById(Long id) {
        return nhanVienRepository.getReferenceById(id);
    }
}
