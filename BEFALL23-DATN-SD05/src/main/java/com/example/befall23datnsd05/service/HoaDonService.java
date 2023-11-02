package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface HoaDonService {


    Page<HoaDon> getByTrangThai(TrangThai trangThai, Integer pageNo, Integer size);

    Page<HoaDon> getByPageAndFilter(int pageNo, String keyWord, LocalDate startDate, LocalDate endDate, TrangThai trangThai);
}
