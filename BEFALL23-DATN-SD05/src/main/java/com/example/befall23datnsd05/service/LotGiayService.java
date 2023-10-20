package com.example.befall23datnsd05.service;


import com.example.befall23datnsd05.entity.LotGiay;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LotGiayService {

    List<LotGiay> getAll();
    LotGiay getById(Long id);

    LotGiay add(LotGiay lotGiay);
    LotGiay update(LotGiay lotGiay);
    void remove(Long id);

    Page<LotGiay> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);
}
