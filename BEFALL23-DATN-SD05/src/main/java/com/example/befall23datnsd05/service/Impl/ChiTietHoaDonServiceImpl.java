package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.repository.HoaDonChiTietRepo;
import com.example.befall23datnsd05.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChiTietHoaDonServiceImpl implements HoaDonChiTietService {
    @Autowired
    private HoaDonChiTietRepo hoaDonChiTietRepo;

    @Override
    public List<HoaDonChiTiet> getCtspById(Long id) {
        return hoaDonChiTietRepo.getHoaDonChiTietByIdHoaDon(id);
    }
}
