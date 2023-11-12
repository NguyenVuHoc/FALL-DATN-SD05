package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.request.GioHangChiTietRequest;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    private final GioHangChiTietRepository gioHangChiTietRepository;

    public GioHangChiTietServiceImpl(GioHangChiTietRepository gioHangChiTietRepository) {
        this.gioHangChiTietRepository = gioHangChiTietRepository;
    }

    @Override
    public List<GioHangChiTiet> getAll() {
        return gioHangChiTietRepository.findAll();
    }

    @Override
    public List<GioHangChiTiet> findGioHangChiTietById(Long id) {
        return gioHangChiTietRepository.findByHoaDon(id);
    }

    @Override
    public Optional<GioHangChiTiet> getOne(Long id) {

        return gioHangChiTietRepository.findById(id);
    }

    @Override
    public GioHangChiTiet save(GioHangChiTiet gioHangChiTiet) {

        return gioHangChiTietRepository.save(gioHangChiTiet);
    }
}
