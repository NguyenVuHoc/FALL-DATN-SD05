package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.KhachHangDangKyDto;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.mapper.KhachHangMapper;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.KhachHangService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository repository;

    @Override
    public KhachHang save(KhachHangDangKyDto dangKyDto) {
        KhachHang khachHang = KhachHangMapper.convertKhachHangDangKyDtoToKhachHang(dangKyDto);
        return repository.save(khachHang);
    }
}
