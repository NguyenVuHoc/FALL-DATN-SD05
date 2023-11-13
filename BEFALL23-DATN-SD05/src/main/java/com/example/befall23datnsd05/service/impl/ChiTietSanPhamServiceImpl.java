package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.*;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public ChiTietSanPham getById(Long id) {
        Optional<ChiTietSanPham> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
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
        if(chiTietSanPham1==null){
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
