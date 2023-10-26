package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.repository.SanPhamRepository;
import com.example.befall23datnsd05.service.SanPhamCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamCustomerServiceImpl implements SanPhamCustomerService {

    @Autowired
    private SanPhamRepository repository;

    @Override
    public List<SanPham> getAll() {
        return repository.findAll();
    }
}
