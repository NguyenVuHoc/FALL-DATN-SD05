package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.HoaDonRepo;
import com.example.befall23datnsd05.request.HoaDonRequest;
import com.example.befall23datnsd05.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo repository;

    @Override
    public List<HoaDon> getAll() {
        return repository.findAll();
    }

    @Override
    public List<HoaDon> getAllByKhachHang(Long id) {
        return repository.getAllByKhachHang(id);
    }

    @Override
    public List<HoaDon> getByTrangThai(TrangThaiDonHang trangThai) {
        return repository.getAllByTrangThai(trangThai);
    }

    @Override
    public List<HoaDon> getByTrangThaiAndKhachHang(TrangThaiDonHang trangThai, Long id) {
//        return repository.getAllByTrangThaiAndKhachHang(trangThai.getTrangThai(), id);
        return repository.findHoaDonByTrangThaiAndKhachHangId(trangThai,id);
    }

    @Override
    public HoaDon findById(Long id) {
        Optional<HoaDon> hoaDon = repository.findById(id);
        if (hoaDon.isPresent()) {
            return hoaDon.get();
        }
        return null;
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return repository.save(hoaDon);
    }

    @Override
    public List<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end, TrangThaiDonHang trangThai) {
        return repository.findHoaDonsByNgayTao(start, end);
    }

    @Override
    public boolean validate(HoaDon hoaDon, TrangThaiDonHang trangThai, String newGhiChu) {
        hoaDon.setTrangThai(trangThai);
        hoaDon.setGhiChu(newGhiChu);
        hoaDon = repository.save(hoaDon);
        return hoaDon.getTrangThai().equals(trangThai);
    }


}
