package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.SanPhamRepository;
import com.example.befall23datnsd05.request.DongSanPhamRequest;
import com.example.befall23datnsd05.request.SanPhamRequest;
import com.example.befall23datnsd05.request.ThuongHieuRequest;
import com.example.befall23datnsd05.service.SanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class SanPhamServiceImpl implements SanPhamService {
    private final SanPhamRepository repository;

    public SanPhamServiceImpl(SanPhamRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<SanPham> getList() {
        return repository.findAll();
    }

    @Override
    public Page<SanPhamCustom> getPage(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("ngayTao").descending());
        return repository.getPageSanPhamCusTom(pageable);
    }


    @Override
    public SanPham save(SanPhamRequest request) {
        SanPham sanPham = new SanPham();
        sanPham.setMa(request.getMa());
        sanPham.setTen(request.getTen());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setDongSanPham(DongSanPham.builder().id(request.getIdDongSanPham()).build());
        sanPham.setThuongHieu(ThuongHieu.builder().id(request.getIdThuongHieu()).build());
        sanPham.setNgayTao(LocalDate.now());
        sanPham.setNgaySua(LocalDate.now());
        sanPham.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return repository.save(sanPham);
    }


    @Override
    public SanPham update(SanPhamRequest request) {
        SanPham sanPham = repository.findById(request.getId()).orElse(null);
        if (sanPham != null) {
            sanPham.setMa(request.getMa());
            sanPham.setTen(request.getTen());
            sanPham.setNgaySua(LocalDate.now());
            sanPham.setTrangThai(request.getTrangThai());
            return repository.save(sanPham);
        }
        return null;
    }


    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SanPham findById(Long id) {
        Optional<SanPham> sanPham = repository.findById(id);
        if (sanPham.isPresent()) {
            return sanPham.get();
        }
        return null;
    }
}
