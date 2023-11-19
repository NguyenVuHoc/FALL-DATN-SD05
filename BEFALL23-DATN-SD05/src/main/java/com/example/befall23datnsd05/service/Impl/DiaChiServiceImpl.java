package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DiaChiRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.DiaChiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DiaChiServiceImpl implements DiaChiService {

    @Autowired
    private DiaChiRepository repository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<DiaChi> getAll() {
        return repository.findAll();
    }

    @Override
    public List<DiaChi> getAllTheoKhachHang(Long id) {
        return repository.getAllTheoKhachHang(id);
    }

    @Override
    public DiaChi getById(Long id) {
        Optional<DiaChi> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public DiaChi add(DiaChiRequest diaChi, Long idKhachHang) {
        DiaChi diaChi1 = new DiaChi();
        diaChi1.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        diaChi1.setDiaChi(diaChi.getDiaChi());
        diaChi1.setSdt(diaChi.getSdt());
        diaChi1.setNgayTao(LocalDate.now());
        diaChi1.setNgaySua(LocalDate.now());
        diaChi1.setGhiChu(diaChi.getGhiChu());
        diaChi1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).orElse(null);
        diaChi1.setKhachHang(khachHang);
        repository.save(diaChi1);
        return diaChi1;
    }

    @Override
    public DiaChi update(DiaChiRequest diaChi, Long id) {
        DiaChi diaChi1 = repository.getReferenceById(id);
        if(diaChi1==null){
            return null;
        }
        diaChi1.setTenNguoiNhan(diaChi.getTenNguoiNhan());
        diaChi1.setDiaChi(diaChi.getDiaChi());
        diaChi1.setSdt(diaChi.getSdt());
        diaChi1.setNgaySua(LocalDate.now());
        diaChi1.setGhiChu(diaChi.getGhiChu());
        repository.save(diaChi1);
        return diaChi1;
    }


    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

}
