package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.repository.HoaDonRepo;
import com.example.befall23datnsd05.service.ThongKeService;
import org.springframework.stereotype.Service;

@Service
public class ThongKeServiceImpl implements ThongKeService {
    private final GioHangChiTietRepository gioHangChiTietRepository;
    private final HoaDonRepo hoaDonRepo;

    public ThongKeServiceImpl(GioHangChiTietRepository gioHangChiTietRepository, HoaDonRepo hoaDonRepo) {
        this.gioHangChiTietRepository = gioHangChiTietRepository;
        this.hoaDonRepo = hoaDonRepo;
    }

    @Override
    public Long doanhThu() {
        return hoaDonRepo.doanhThu();
    }

    @Override
    public Long soDonHuy() {
        return hoaDonRepo.soDonHuy();
    }

    @Override
    public Long soSanPhamHoanTra() {
        return gioHangChiTietRepository.sanPhamHoanTra();
    }
}
