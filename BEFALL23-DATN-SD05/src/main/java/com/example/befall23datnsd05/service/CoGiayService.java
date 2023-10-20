package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.CoGiay;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CoGiayService {

    List<CoGiay> getAll();
    CoGiay getById(Long id);

    CoGiay add(CoGiay coGiay);
    CoGiay update(CoGiay coGiay);
    void remove(Long id);

    Page<CoGiay> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);

}
