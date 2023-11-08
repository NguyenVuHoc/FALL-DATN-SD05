package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DeGiayService {

    List<DeGiay> getAll();
    DeGiay getById(Long id);

    DeGiay add(DeGiay deGiay);
    DeGiay update(DeGiay deGiay);
    void remove(Long id);

    Page<DeGiay> phanTrang(Integer pageNo, Integer size);
    Integer chuyenPage(Integer pageNo);

    boolean existByMa(String ma);
    boolean existsByTen(String ten);
    boolean existsByTenAndIdNot(String ten, Long id);

    Page<DeGiay> timTen(String ten, Integer pageNo, Integer size);

}
