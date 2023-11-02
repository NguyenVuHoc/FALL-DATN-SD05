package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;


    @Override
    public Page<KhachHang> phanTrang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size, Sort.by(Sort.Order.desc("id")));
        return khachHangRepository.findAll(pageable);
    }

    @Override
    public KhachHang add(KhachHangRequest khachHangRequest) {
        KhachHang khachHang1 = new KhachHang();
        khachHang1.setMa(khachHangRequest.getMa());
        khachHang1.setTen(khachHangRequest.getTen());
        khachHang1.setSdt(khachHangRequest.getSdt());
        khachHang1.setGioiTinh(GioiTinh.valueOf(khachHangRequest.getGioiTinh()));
        khachHang1.setNgayTao(LocalDate.now());
        khachHang1.setNgaySua(LocalDate.now());
        khachHang1.setEmail(khachHangRequest.getEmail());
        khachHang1.setMatKhau(khachHangRequest.getMatKhau());
        khachHang1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return khachHangRepository.save(khachHang1);
    }

    @Override
    public KhachHang update(KhachHangRequest khachHangRequest) {
        if (khachHangRequest == null) {
            return null;
        }
        KhachHang existingKhachHang = khachHangRepository.getReferenceById(khachHangRequest.getId());
        if (existingKhachHang == null) {
            return null;
        }
        existingKhachHang.setMa(khachHangRequest.getMa());
        existingKhachHang.setTen(khachHangRequest.getTen());
        existingKhachHang.setSdt(khachHangRequest.getSdt());
        existingKhachHang.setGioiTinh(GioiTinh.valueOf(khachHangRequest.getGioiTinh()));
        existingKhachHang.setNgaySua(LocalDate.now());
        existingKhachHang.setEmail(khachHangRequest.getEmail());
        existingKhachHang.setMatKhau(khachHangRequest.getMatKhau());
        existingKhachHang.setTrangThai(khachHangRequest.getTrangThai());

        try {
            khachHangRepository.save(existingKhachHang);
            return existingKhachHang;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        khachHangRepository.deleteById(id);
    }

    @Override
    public KhachHang getById(Long id) {
        Optional<KhachHang> optional = khachHangRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public boolean exist(String ma) {
        return khachHangRepository.existsByMa(ma);
    }

    @Override
    public Integer chuyenPage(Integer pageNo) {
        Integer sizeList = khachHangRepository.findAll().size();
        Integer pageCount = (int) Math.ceil((double) sizeList/5);
        if (pageNo >= pageCount){
            pageNo = 0;
        }else if(pageNo < 0){
            pageNo = pageCount -1;
        }
        return pageNo;
    }

    @Override
    public Page<KhachHang> timTen(String ten, Integer pageNo, Integer size) {
        Pageable pageable1 = PageRequest.of(pageNo , size, Sort.by(Sort.Order.desc("id")));
        return khachHangRepository.findByTenContains(ten,pageable1);
    }

    @Override
    public Page<KhachHang> getTrangThaiHoatDong(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return khachHangRepository.getTrangThaiHoatDong(pageable);
    }

    @Override
    public Page<KhachHang> getTrangThaiDungHoatDong(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return khachHangRepository.getTrangThaiDungHoatDong(pageable);
    }



}
