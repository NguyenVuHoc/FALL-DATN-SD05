package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DongSanPhamRepository;
import com.example.befall23datnsd05.service.DongSanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class DongSanPhamServiceImpl implements DongSanPhamService {
    private final DongSanPhamRepository repository;

    public DongSanPhamServiceImpl(DongSanPhamRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DongSanPham> getList() {
        return repository.findAll();
    }

    @Override
    public Page<DongSanPham> getAll(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.findAll(pageable);
    }

    @Override
    public void save(DongSanPham dongSanPham) {
        DongSanPham dongSanPham1 = new DongSanPham();
        dongSanPham1.setMa(dongSanPham.getMa());
        dongSanPham1.setTen(dongSanPham.getTen());
        dongSanPham1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        dongSanPham1.setNgaySua(LocalDate.now());
        dongSanPham1.setNgayTao(LocalDate.now());
        repository.save(dongSanPham1);

    }

    @Override
    public void update(DongSanPham dongSanPham) {
        DongSanPham dongSanPham1 = repository.findById(dongSanPham.getId()).orElse(null);
        if (dongSanPham1 != null) {
            dongSanPham1.setMa(dongSanPham.getMa());
            dongSanPham1.setTen(dongSanPham.getTen());
            dongSanPham1.setTrangThai(dongSanPham.getTrangThai());
            dongSanPham1.setNgaySua(LocalDate.now());
            repository.save(dongSanPham1);
        }
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public DongSanPham findById(Long id) {
        Optional<DongSanPham> dongSanPham = repository.findById(id);
        if (dongSanPham.isPresent()) {
            return dongSanPham.get();
        }
        return null;
    }
}
