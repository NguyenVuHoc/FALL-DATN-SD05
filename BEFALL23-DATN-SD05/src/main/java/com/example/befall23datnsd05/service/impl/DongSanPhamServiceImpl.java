package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.custom.DongSanphamCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DongSanPhamRepository;
import com.example.befall23datnsd05.request.DongSanPhamRequest;
import com.example.befall23datnsd05.service.DongSanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<DongSanPham> getPage(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by("ngayTao").descending());
        return repository.getPageDongSanPhamCusTom(pageable);
    }

    @Override
    public Page<DongSanPham> getPageByActivity(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.getDongSpByTrangThaiHoatDong(pageable);
    }


    @Override
    public Page<DongSanPham> getPageByInActivity(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return repository.getDongSpByTrangThaiDungHoatDong(pageable);
    }


    @Override
    public DongSanPham save(DongSanPhamRequest request) {
        DongSanPham dongSanPham = new DongSanPham();
        dongSanPham.setMa(request.getMa());
        dongSanPham.setTen(request.getTen());
        dongSanPham.setNgayTao(LocalDate.now());
        dongSanPham.setNgaySua(LocalDate.now());
        dongSanPham.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return repository.save(dongSanPham);
    }


    @Override
    public DongSanPham update(DongSanPhamRequest request) {
        DongSanPham dongSanPham1 = repository.findById(request.getId()).orElse(null);
        if (dongSanPham1 != null) {
            dongSanPham1.setMa(request.getMa());
            dongSanPham1.setTen(request.getTen());
            dongSanPham1.setNgaySua(LocalDate.now());
            dongSanPham1.setTrangThai(request.getTrangThai());
            return repository.save(dongSanPham1);
        }
        return null;
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
    public Integer tranferPageTest(Integer pageNo, Page<DongSanphamCustom> page) {
        return null;
    }

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByMa(ma);
    }


//    @Override
//    public boolean existByTenUpdate(String ten, String tenHienTai) {
//        return repository.existsByTenUpdate(ten, tenHienTai);
//    }

    @Override
    public boolean existsByTen(String ten) {
        return repository.existsByTen(ten);
    }

    @Override
    public boolean existsByTenAndIdNot(String ten, Long id) {
        return repository.existsByTenAndIdNot(ten, id);
    }


}
