package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.repository.GioHangRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.BanHangCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BanHangCustomerServiceImpl implements BanHangCustomerService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private GioHangRepository gioHangRepository;


    @Override
    public void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getReferenceById(chiTietSanPhamId);
        GioHang gioHang = gioHangRepository.getByKhachHangId(khachHangId);

        if (gioHang == null){
            gioHang = new GioHang();
            gioHang.setKhachHang(khachHangRepository.getReferenceById(khachHangId));
            gioHangRepository.save(gioHang);
        }

        GioHangChiTiet gioHangChiTiet1 = gioHangChiTietRepository.findByGioHangAndChiTietSanPham(gioHang, chiTietSanPham);
        if (gioHangChiTiet1 != null){
            gioHangChiTiet1.setSoLuong(gioHangChiTiet1.getSoLuong() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet1);

        }else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
    }

    @Override
    public void xoaKhoiGioHang(Long id) {
        gioHangChiTietRepository.deleteById(id);
    }
}
