package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.AnhSanPhamRepository;
import com.example.befall23datnsd05.service.AnhSanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AnhServiceImpl implements AnhSanPhamService {
    private final AnhSanPhamRepository repository;

    public AnhServiceImpl(AnhSanPhamRepository repository) {
        this.repository = repository;
    }


    @Override
    public AnhSanPham save(AnhSanPham anhSanPham) {
        return repository.save(anhSanPham);
    }

    @Override
    public AnhSanPham update(AnhSanPham anhSanPham) {
        AnhSanPham anhSanPham1 = repository.findById(anhSanPham.getId()).orElse(null);
        if (anhSanPham1 != null) {
            anhSanPham.setUrl(anhSanPham1.getUrl());
            return repository.save(anhSanPham);
        }
        return null;
    }

    @Override
    public List<AnhSanPham> getAnh(SanPham sanPham) {
        return repository.findBySanPham(sanPham);
    }

    @Transactional
    @Override
    public void deleteByIdSp(Long id) {
        List<AnhSanPham> anhSanPhams = repository.findBySanPhamId(id);
        repository.deleteInBatch(anhSanPhams);
    }


}
