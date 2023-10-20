package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.MauSac;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MauSacService {

    List<MauSac> getAll();
    MauSac getById(Long id);

    MauSac add(MauSac mauSac);
    MauSac update(MauSac mauSac);
    void remove(Long id);

    Page<MauSac> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);
}
