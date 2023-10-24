package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DongSanPhamRepository;
import com.example.befall23datnsd05.repository.SanPhamRepository;
import com.example.befall23datnsd05.repository.ThuongHieuRepository;
import com.example.befall23datnsd05.request.SanPhamRequest;
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
    private final DongSanPhamRepository dongSanPhamRepository;
    private final ThuongHieuRepository thuongHieuRepository;

    public SanPhamServiceImpl(SanPhamRepository repository, DongSanPhamRepository dongSanPhamRepository, ThuongHieuRepository thuongHieuRepository) {
        this.repository = repository;
        this.dongSanPhamRepository = dongSanPhamRepository;
        this.thuongHieuRepository = thuongHieuRepository;
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
    public Page<SanPhamCustom> getPageByActivity(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.getSanPhamByTrangThaiHoatDong(pageable);
    }

    @Override
    public Page<SanPhamCustom> getPageByInActivity(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.getSanPhamByTrangThaiDungHoatDong(pageable);
    }


    @Override
    public SanPham save(SanPhamRequest request) {
        SanPham sanPham = new SanPham();
        sanPham.setMa(request.getMa());
        sanPham.setTen(request.getTen());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setListAnhSanPham(request.getListAnh());
        sanPham.setAnhChinh(request.getAnhChinh());
        DongSanPham dongSanPham = dongSanPhamRepository.findById(request.getDongSanPham()).orElse(null);
        sanPham.setDongSanPham(dongSanPham);
        ThuongHieu thuongHieu = thuongHieuRepository.findById(request.getThuongHieu()).orElse(null);
        sanPham.setThuongHieu(thuongHieu);
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
            sanPham.setMoTa(request.getMoTa());
            sanPham.setListAnhSanPham(request.getListAnh());
            sanPham.setAnhChinh(request.getAnhChinh());
            DongSanPham dongSanPham = dongSanPhamRepository.findById(request.getDongSanPham()).orElse(null);
            sanPham.setDongSanPham(dongSanPham);
            ThuongHieu thuongHieu = thuongHieuRepository.findById(request.getThuongHieu()).orElse(null);
            sanPham.setThuongHieu(thuongHieu);
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

    @Override
    public Integer tranferPage(Integer pageNo) {
        Integer sizeList = repository.findAll().size();
        System.out.println(sizeList);
        Integer pageCount = (int) Math.ceil((double) sizeList / 5);
        System.out.println(pageCount);
        if (pageNo >= pageCount) {
            pageNo = 0;
        } else if (pageNo < 0) {
            pageNo = pageCount - 1;
        }
        System.out.println(pageNo);
        return pageNo;

    }

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByMa(ma);
    }

    @Override
    public boolean existsByTen(String ten) {
        return repository.existsByTen(ten);
    }

    @Override
    public boolean existsByTenAndIdNot(String ten, Long id) {
        return repository.existsByTenAndIdNot(ten, id);
    }

}
