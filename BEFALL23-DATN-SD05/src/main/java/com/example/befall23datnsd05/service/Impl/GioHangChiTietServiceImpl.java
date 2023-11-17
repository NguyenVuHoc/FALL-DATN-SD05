package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.repository.GioHangRepository;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public List<GioHangChiTiet> getAll(Long idKhachHang) {
        GioHang gioHang = gioHangRepository.getByKhachHangId(idKhachHang);
        return gioHangChiTietRepository.findAllByGioHang(gioHang.getId());
    }
}
