package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.KhuyenMaiRequest;
import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import com.example.befall23datnsd05.repository.KhuyenMaiRepository;
import com.example.befall23datnsd05.service.KhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiServiceImpl implements KhuyenMaiService {

    @Autowired
    KhuyenMaiRepository repository;

    @Override
    public List<KhuyenMai> getList() {
        return repository.findAll();
    }

    @Override
    public void updateTrangThai() {
        repository.updateTrangThaiDangHoatDong();
        repository.updateTrangThaiDungHoatDong1();
        repository.updateTrangThaiDungHoatDong2();
        repository.updateTrangThaiSapDienRa();
    }

    @Override
    public List<KhuyenMai> getByTrangThai(TrangThaiKhuyenMai trangThaiKhuyenMai) {
        return repository.getAllByTrangThai(trangThaiKhuyenMai);
    }

    @Override
    public KhuyenMai add(KhuyenMaiRequest khuyenMaiRequest) {
        KhuyenMai km = new KhuyenMai();
        LocalDateTime time = LocalDateTime.now();
        String maKM = "KM" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        km.setMa(maKM);
        km.setTen(khuyenMaiRequest.getTen());
        km.setMoTa(khuyenMaiRequest.getMoTa());
        km.setMucGiamGia(khuyenMaiRequest.getMucGiamGia());
        km.setNgayTao(LocalDate.now());
        km.setNgaySua(LocalDate.now());
        km.setNgayBatDau(khuyenMaiRequest.getNgayBatDau());
        km.setNgayKetThuc(khuyenMaiRequest.getNgayKetThuc());
        km.setTrangThai(khuyenMaiRequest.htTrangThai());
        return repository.save(km);
    }

    @Override
    public KhuyenMai update(KhuyenMaiRequest khuyenMaiRequest) {
        KhuyenMai km = repository.findById(khuyenMaiRequest.getId()).orElse(null);
        km.setTen(khuyenMaiRequest.getTen());
        km.setMoTa(khuyenMaiRequest.getMoTa());
        km.setMucGiamGia(khuyenMaiRequest.getMucGiamGia());
        km.setNgaySua(LocalDate.now());
        km.setNgayBatDau(khuyenMaiRequest.getNgayBatDau());
        km.setNgayKetThuc(khuyenMaiRequest.getNgayKetThuc());
        km.setTrangThai(khuyenMaiRequest.htTrangThai());
        repository.save(km);
        return km;
    }

    @Override
    public KhuyenMai getById(Long id) {
        Optional<KhuyenMai> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public void huy(Long id) {
        KhuyenMai khuyenMai = repository.findById(id).orElse(null);
        if (khuyenMai != null) {
            khuyenMai.setNgayKetThuc(LocalDate.now().minusDays(1));
            khuyenMai.setTrangThai(TrangThaiKhuyenMai.DUNG_HOAT_DONG);
            repository.save(khuyenMai);
        }
    }

    @Override
    public List<KhuyenMai> findKhuyenMai(LocalDate start, LocalDate end, TrangThaiKhuyenMai trangThaiKhuyenMai) {
        return repository.findKhuyenMaisByNgayBatDauAndNgayKetThuc(start, end);
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
