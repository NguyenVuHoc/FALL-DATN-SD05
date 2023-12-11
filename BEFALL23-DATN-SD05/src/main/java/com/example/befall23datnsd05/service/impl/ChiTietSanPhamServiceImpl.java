package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.CoGiayRepository;
import com.example.befall23datnsd05.repository.DeGiayRepository;
import com.example.befall23datnsd05.repository.KichThuocRepository;
import com.example.befall23datnsd05.repository.LotGiayRepository;
import com.example.befall23datnsd05.repository.MauSacRepository;
import com.example.befall23datnsd05.repository.SanPhamRepository;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {

    @Autowired
    private ChiTietSanPhamRepository repository;

    @Autowired
    private DeGiayRepository deGiayRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Autowired
    private LotGiayRepository lotGiayRepository;

    @Autowired
    private CoGiayRepository coGiayRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;


    @Override
    public List<ChiTietSanPham> getAll() {
        return repository.findAll();
    }

    @Override
    public List<ChiTietSanPham> getAllSanPhamKhuyenMai(Long idKM) {
        return repository.getAllSpKhuyenMai(idKM);
    }

    @Override
    public List<ChiTietSanPham> getSpCoKhuyenMai(Long idKM) {
        return repository.getAllSpCoKhuyenMai(idKM);
    }

    //    Chức năng ctsp với khuyến mại
    @Override
    public void updateIdKhuyenMai(Long idKM, Long idCtsp) {
        repository.updateIdKhuyenMai(idKM, idCtsp);
    }

    @Override
    public void deleteIdKhuyenMai(Long idCtsp) {
        repository.deleteIdKhuyenMai(idCtsp);
    }

    @Override
    public void updateGiaBan(Long id) {
        ChiTietSanPham ctsp = repository.findById(id).orElse(null);
        if (ctsp != null) {
            ctsp.setGiaBan(ctsp.tinhGiaSauGiamGia());
            repository.save(ctsp);
        }
    }

    @Override
    @Transactional
    public void autoUpdateGia() {
        List<ChiTietSanPham> list = repository.findAll();
        for (ChiTietSanPham ctsp : list) {
            BigDecimal giaSauGiamGia = ctsp.tinhGiaSauGiamGia();
            ctsp.setGiaBan(giaSauGiamGia);
        }
        repository.saveAll(list);
    }

    @Override
    public ChiTietSanPham save(ChiTietSanPham chiTietSanPham) {
        repository.save(chiTietSanPham);
        return chiTietSanPham;
    }

    @Override
    public List<ChiTietSanPham> fillAllDangHoatDong() {
        return repository.fillAllDangHoatDong();
    }

//   Hết chức năng ctsp với khuyến mại

    @Override
    public ChiTietSanPham getById(Long id) {
        Optional<ChiTietSanPham> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public ChiTietSanPham add(ChiTietSanPhamRequest chiTietSanPham) {
        ChiTietSanPham chiTietSanPham1 = new ChiTietSanPham();
        SanPham sanPham = sanPhamRepository.findById(chiTietSanPham.getSanPham()).orElse(null);
        chiTietSanPham1.setSanPham(sanPham);
        DeGiay deGiay = deGiayRepository.findById(chiTietSanPham.getDeGiay()).orElse(null);
        chiTietSanPham1.setDeGiay(deGiay);
        MauSac mauSac = mauSacRepository.findById(chiTietSanPham.getMauSac()).orElse(null);
        chiTietSanPham1.setMauSac(mauSac);
        KichThuoc kichThuoc = kichThuocRepository.findById(chiTietSanPham.getKichThuoc()).orElse(null);
        chiTietSanPham1.setKichThuoc(kichThuoc);
        LotGiay lotGiay = lotGiayRepository.findById(chiTietSanPham.getLotGiay()).orElse(null);
        chiTietSanPham1.setLotGiay(lotGiay);
        CoGiay coGiay = coGiayRepository.findById(chiTietSanPham.getCoGiay()).orElse(null);
        chiTietSanPham1.setCoGiay(coGiay);
        chiTietSanPham1.setSoLuongTon(chiTietSanPham.getSoLuongTon());
        chiTietSanPham1.setGiaMacDinh(chiTietSanPham.getGiaMacDinh());
        chiTietSanPham1.setGiaBan(chiTietSanPham.getGiaBan());
        chiTietSanPham1.setNgayTao(LocalDate.now());
        chiTietSanPham1.setNgaySua(LocalDate.now());
        chiTietSanPham1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        repository.save(chiTietSanPham1);
        return chiTietSanPham1;
    }

    @Override
    public ChiTietSanPham update(ChiTietSanPhamRequest chiTietSanPham) {
        ChiTietSanPham chiTietSanPham1 = repository.getReferenceById(chiTietSanPham.getId());
        if (chiTietSanPham1 == null) {
            return null;
        }
        SanPham sanPham = sanPhamRepository.findById(chiTietSanPham.getSanPham()).orElse(null);
        chiTietSanPham1.setSanPham(sanPham);
        DeGiay deGiay = deGiayRepository.findById(chiTietSanPham.getDeGiay()).orElse(null);
        chiTietSanPham1.setDeGiay(deGiay);
        MauSac mauSac = mauSacRepository.findById(chiTietSanPham.getMauSac()).orElse(null);
        chiTietSanPham1.setMauSac(mauSac);
        KichThuoc kichThuoc = kichThuocRepository.findById(chiTietSanPham.getKichThuoc()).orElse(null);
        chiTietSanPham1.setKichThuoc(kichThuoc);
        LotGiay lotGiay = lotGiayRepository.findById(chiTietSanPham.getLotGiay()).orElse(null);
        chiTietSanPham1.setLotGiay(lotGiay);
        CoGiay coGiay = coGiayRepository.findById(chiTietSanPham.getCoGiay()).orElse(null);
        chiTietSanPham1.setCoGiay(coGiay);
        chiTietSanPham1.setSoLuongTon(chiTietSanPham.getSoLuongTon());
        chiTietSanPham1.setGiaMacDinh(chiTietSanPham.getGiaMacDinh());
        chiTietSanPham1.setGiaBan(chiTietSanPham.getGiaBan());
        chiTietSanPham1.setNgaySua(LocalDate.now());
        chiTietSanPham1.setTrangThai(chiTietSanPham.getTrangThai());
        repository.save(chiTietSanPham1);
        return chiTietSanPham1;
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<ChiTietSanPham> getByTrangThai(TrangThai trangThai) {
        return repository.getAllByTrangThai(trangThai);
    }


}
