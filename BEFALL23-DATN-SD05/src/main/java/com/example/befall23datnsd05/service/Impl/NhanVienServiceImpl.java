package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.NhanVienRequest;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<NhanVien> getList() {
        return nhanVienRepository.findAll();
    }

    @Override
    public List<NhanVien> getByTrangThai(TrangThai trangThai) {
        return nhanVienRepository.getAllByTrangThai(trangThai);
    }

    @Override
    public NhanVien add(NhanVienRequest nhanVienRequest) {
        NhanVien nhanVien1 = new NhanVien();
        nhanVien1.setMa(nhanVienRequest.getMa());
        nhanVien1.setTen(nhanVienRequest.getTen());
        nhanVien1.setSdt(nhanVienRequest.getSdt());
        nhanVien1.setGioiTinh(GioiTinh.valueOf(nhanVienRequest.getGioiTinh()));
        nhanVien1.setNgayTao(LocalDate.now());
        nhanVien1.setNgaySua(LocalDate.now());
        nhanVien1.setEmail(nhanVienRequest.getEmail());
        nhanVien1.setMatKhau(nhanVienRequest.getMatKhau());
        nhanVien1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return nhanVienRepository.save(nhanVien1);
    }

    @Override
    public NhanVien update(NhanVienRequest nhanVienRequest) {
        if (nhanVienRequest == null) {
            return null;
        }
        NhanVien existingNhanVien = nhanVienRepository.getReferenceById(nhanVienRequest.getId());
        if (existingNhanVien == null) {
            return null;
        }
        existingNhanVien.setMa(nhanVienRequest.getMa());
        existingNhanVien.setTen(nhanVienRequest.getTen());
        existingNhanVien.setSdt(nhanVienRequest.getSdt());
        existingNhanVien.setGioiTinh(GioiTinh.valueOf(nhanVienRequest.getGioiTinh()));
        existingNhanVien.setNgaySua(LocalDate.now());
        existingNhanVien.setEmail(nhanVienRequest.getEmail());
        existingNhanVien.setMatKhau(nhanVienRequest.getMatKhau());
        existingNhanVien.setTrangThai(nhanVienRequest.getTrangThai());

        try {
            nhanVienRepository.save(existingNhanVien);
            return existingNhanVien;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        nhanVienRepository.deleteById(id);
    }

    @Override
    public NhanVien getById(Long id) {
        Optional<NhanVien> optional = nhanVienRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public Page<NhanVien> timTen(String ten, Integer pageNo, Integer size) {
        Pageable pageable1 = PageRequest.of(pageNo , size, Sort.by(Sort.Order.desc("id")));
        return nhanVienRepository.findByTenContains(ten,pageable1);
    }

    @Override
    public boolean existByMa(String ma) {
        return nhanVienRepository.existsByMa(ma);
    }

    @Override
    public boolean existsByTen(String ten) {
        return nhanVienRepository.existsByTen(ten);
    }

    @Override
    public boolean existsByTenAndIdNot(String ten, Long id) {
        return nhanVienRepository.existsByTenAndIdNot(ten, id);
    }

}
