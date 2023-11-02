package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.HoaDonRepo;
import com.example.befall23datnsd05.service.HoaDonService;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo repository;




    @Override
    public Page<HoaDon> getByTrangThai(TrangThai trangThai, Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return repository.getAllByTrangThai(trangThai, pageable);
    }

    @Override
    public Page<HoaDon> getByPageAndFilter(int pageNo, String keyWord, LocalDate startDate, LocalDate endDate, TrangThai trangThai) {
        Pageable pageable = PageRequest.of(pageNo, 5);

//        getAll
        if (StringUtils.isNullOrEmpty(keyWord) && startDate == null && endDate == null&& trangThai==null) {
            return repository.findAll(pageable);
        }
//        getByKeyWord
        else if (startDate == null && endDate == null && trangThai==null) {
            return repository.findHoaDonsByKeyWord(keyWord, pageable);
        }
//        getNgayTao
        else if (StringUtils.isNullOrEmpty(keyWord)&& trangThai==null) {
            return repository.findHoaDonsByNgayTao(startDate, endDate, pageable);
        }
//        get TrangThai
        else if (StringUtils.isNullOrEmpty(keyWord) && startDate == null && endDate == null && trangThai != null) {
            return repository.findHoaDonsByTrangThai(trangThai,pageable);
//            getByTrangThaiAndKeyWord
        } else if (trangThai != null && startDate == null && endDate == null) {
            return repository.findHoaDonsByKeyWordAndTrangThai(keyWord, trangThai, pageable);
//            getByTrangThaiAndNgayTao
        } else if (trangThai != null && StringUtils.isNullOrEmpty(keyWord)) {
            return repository.findHoaDonsByNgayTaoAndTrangThai(startDate, endDate, trangThai, pageable);
        }
//        getByTrangThaiAndKeyWordAndNgayTao
        else if (trangThai != null) {
            return repository.findHoaDonsByNgayTaoAndKeyWordAndTrangThai(startDate, endDate, keyWord, trangThai, pageable);
        }
        //getByNgayTaoAndKeyWord
        else {
            return repository.findHoaDonsByKeyWordAndNgayTao(keyWord, startDate, endDate, pageable);
        }
    }
}
