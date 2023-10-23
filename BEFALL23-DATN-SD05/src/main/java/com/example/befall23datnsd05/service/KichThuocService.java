package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import org.springframework.data.domain.Page;

import java.util.List;

public interface KichThuocService {

    List<KichThuoc> getAll();
    KichThuoc getById(Long id);

    KichThuoc add(KichThuoc kichThuoc);
    KichThuoc update(KichThuoc kichThuoc);
    void remove(Long id);

    Page<KichThuoc> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);

    boolean exist(String ma);

    Page<KichThuoc> timTen(String ten, Integer pageNo, Integer size);

}
