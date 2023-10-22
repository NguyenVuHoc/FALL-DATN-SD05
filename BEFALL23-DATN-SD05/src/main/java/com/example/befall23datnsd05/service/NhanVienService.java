package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.NhanVienRequest;
import com.example.befall23datnsd05.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhanVienService {

    Page<NhanVien> phanTrang(Integer pageNo, Integer size);

    NhanVien add(NhanVienRequest nhanVienRequest);

    NhanVien update(NhanVienRequest nhanVienRequest);

    void remove(Long id);

    NhanVien getById(Long id);

    boolean exist(String ma);

    Integer chuyenPage(Integer pageNo);

    Page<NhanVien> timTen(String ten,Integer pageNo, Integer size);

    Page<NhanVien> getTrangThaiHoatDong(Integer pageNo, Integer size);

    Page<NhanVien> getTrangThaiDungHoatDong(Integer pageNo, Integer size);
}
