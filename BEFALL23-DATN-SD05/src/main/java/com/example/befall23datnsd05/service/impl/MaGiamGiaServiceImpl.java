package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.MaGiamGiaRequest;
import com.example.befall23datnsd05.entity.MaGiamGia;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import com.example.befall23datnsd05.repository.MaGiamGiaRepository;
import com.example.befall23datnsd05.service.MaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MaGiamGiaServiceImpl implements MaGiamGiaService {

    @Autowired
    private MaGiamGiaRepository repository;

    @Override
    public List<MaGiamGia> getAll() {
        return repository.findAll();
    }

    @Override
    public List<MaGiamGia> getListHoatDong() {
        return repository.listMaGiamGiaHoatDong();
    }

    @Override
    public List<MaGiamGia> getByTrangThai(TrangThaiKhuyenMai trangThaiKhuyenMai) {
        return repository.getAllByTrangThai(trangThaiKhuyenMai);
    }

    @Override
    public List<MaGiamGia> findMaGiamGia(LocalDate start, LocalDate end, TrangThaiKhuyenMai trangThaiKhuyenMai) {
        return repository.findMaGiamGiasByByNgayBatDauAndNgayKetThuc(start, end);
    }

    @Override
    public void updateTrangThai() {
        repository.updateTrangThaiDangHoatDong();
        repository.updateTrangThaiDungHoatDong1();
        repository.updateTrangThaiDungHoatDong2();
        repository.updateTrangThaiSapDienRa();
    }

    @Override
    public MaGiamGia add(MaGiamGiaRequest maGiamGiaRequest) {
        MaGiamGia mgg = new MaGiamGia();
        LocalDateTime time = LocalDateTime.now();
        String ma = "MGG" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        mgg.setMa(ma);
        mgg.setTen(maGiamGiaRequest.getTen());
        mgg.setMoTa(maGiamGiaRequest.getMoTa());
        mgg.setMucGiamGia(maGiamGiaRequest.getMucGiamGia());
        mgg.setMucGiamToiDa(maGiamGiaRequest.getMucGiamToiDa());
        mgg.setNgayTao(LocalDate.now());
        mgg.setNgaySua(LocalDate.now());
        mgg.setSoLuong(maGiamGiaRequest.getSoLuong());
        mgg.setGiaTriDonHang(maGiamGiaRequest.getGiaTriDonHang());
        mgg.setNgayBatDau(maGiamGiaRequest.getNgayBatDau());
        mgg.setNgayKetThuc(maGiamGiaRequest.getNgayKetThuc());
        mgg.setTrangThai(maGiamGiaRequest.htTrangThai());
        return repository.save(mgg);
    }

    @Override
    public MaGiamGia getById(Long id) {
        Optional<MaGiamGia> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public MaGiamGia update(MaGiamGiaRequest maGiamGiaRequest) {
        MaGiamGia mgg = repository.findById(maGiamGiaRequest.getId()).orElse(null);
        mgg.setTen(maGiamGiaRequest.getTen());
        mgg.setMoTa(maGiamGiaRequest.getMoTa());
        mgg.setMucGiamGia(maGiamGiaRequest.getMucGiamGia());
        mgg.setMucGiamToiDa(maGiamGiaRequest.getMucGiamToiDa());
        mgg.setNgaySua(LocalDate.now());
        mgg.setSoLuong(maGiamGiaRequest.getSoLuong());
        mgg.setGiaTriDonHang(maGiamGiaRequest.getGiaTriDonHang());
        mgg.setNgayBatDau(maGiamGiaRequest.getNgayBatDau());
        mgg.setNgayKetThuc(maGiamGiaRequest.getNgayKetThuc());
        mgg.setTrangThai(maGiamGiaRequest.htTrangThai());
        return repository.save(mgg);
    }

    @Override
    public void huy(Long id) {
        MaGiamGia maGiamGia = repository.findById(id).orElse(null);
        if (maGiamGia != null) {
            maGiamGia.setTrangThai(TrangThaiKhuyenMai.DUNG_HOAT_DONG);
            repository.save(maGiamGia);
        }
    }

    @Override
    public boolean existsByTen(String ten) {
        return repository.existsByTen(ten);
    }

    @Override
    public boolean existsByTenAndIdNot(String ten, Long id) {
        return repository.existsByTenAndIdNot(ten, id);
    }

    @Override
    public List<MaGiamGia> layList(Long tongGiaTri) {
        return repository.getAllByGiaTriDonHang(tongGiaTri);
    }
}
