package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.Befall23DatnSd05Application;
import com.example.befall23datnsd05.dto.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.HoaDonChiTietRepository;
import com.example.befall23datnsd05.repository.HoaDonRepository;
import com.example.befall23datnsd05.service.BanHangService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BanHangServiceImpl implements BanHangService {

    @Autowired
    private HoaDonRepository hoaDonRepository;


    private final HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<HoaDon> getHoaDonCho() {
        List<HoaDon> listHoaDonCho = new ArrayList<>();
        for (HoaDon hoaDon: hoaDonRepository.findAll()){
            if (hoaDon.getTrangThai() == TrangThai.DUNG_HOAT_DONG){
                listHoaDonCho.add(hoaDon);
            }
        }
        return listHoaDonCho;
    }

    @Override
    public List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(Long idHoaDon) {
        return hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
    }

    @Override
    public Optional<HoaDon> getHoaDonById(Long idHoaDon) {
        return Optional.of(hoaDonRepository.getReferenceById(idHoaDon));
    }

    @Override
    public List<ChiTietSanPham> getChiTietSanPham() {
        List<ChiTietSanPham> listChiTietSanPham = new ArrayList<>();
        for (ChiTietSanPham chiTietSanPham: chiTietSanPhamRepository.findAll()){
            if (chiTietSanPham.getTrangThai() == TrangThai.DANG_HOAT_DONG){
                listChiTietSanPham.add(chiTietSanPham);
            }
        }
        return listChiTietSanPham;
    }

    @Override
    public HoaDon themHoaDon(HoaDon hoaDon) {
        return hoaDonRepository.save(hoaDon);
    }

//    public static void main(String[] args) {
//
//        List<HoaDonChiTiet> list = new BanHangServiceImpl(new HoaDonChiTietRepository() {
//        }HoaDonChiTietRepository).getHoaDonChiTietByIdHoaDon(Long.valueOf(1));
//        for (HoaDonChiTiet hoaDonChiTiet: list){
//            System.out.printf(hoaDonChiTiet.toString());
//        }
//    }
}
