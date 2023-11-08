package com.example.befall23datnsd05.service;


import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KhachHangService {

    List<KhachHang> getList();

    List<KhachHang> getByTrangThai(TrangThai trangThai);

    KhachHang add(KhachHangRequest khachHangRequest);

    KhachHang update(KhachHangRequest khachHangRequest);

    void remove(Long id);

    KhachHang getById(Long id);

    boolean existsBySdt(String sdt);
    boolean existsBySdtAndIdNot(String sdt,Long id);

}

