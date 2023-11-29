package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DiaChiRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<KhachHang> getList() {
        return khachHangRepository.getListKhachHang();
    }

    @Override
    public List<KhachHang> getByTrangThai(TrangThai trangThai) {
        return khachHangRepository.getAllByTrangThai(trangThai);
    }

    @Override
    public KhachHang add(KhachHangRequest khachHangRequest) {
        KhachHang khachHang1 = new KhachHang();
        khachHang1.setMa(khachHangRequest.getMa());
        khachHang1.setTen(khachHangRequest.getTen());
        khachHang1.setSdt(khachHangRequest.getSdt());
        khachHang1.setGioiTinh(GioiTinh.valueOf(khachHangRequest.getGioiTinh()));
        khachHang1.setNgayTao(LocalDate.now());
        khachHang1.setNgaySua(LocalDate.now());
        khachHang1.setEmail(khachHangRequest.getEmail());
        khachHang1.setMatKhau(khachHangRequest.getMatKhau());
        List<DiaChi> diaChiList = new ArrayList<>();
        for (String address : khachHangRequest.getDiaChi()) {
            DiaChi diaChi = new DiaChi();
            diaChi.setDiaChi(address);
            diaChi.setSdt(khachHang1.getSdt());
            diaChi.setTenNguoiNhan(khachHang1.getTen());
            diaChi.setKhachHang(khachHang1);
            diaChiList.add(diaChi);
        }
        khachHang1.setListDiaChi(diaChiList);
        khachHang1.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return khachHangRepository.save(khachHang1);
    }

    @Override
    public KhachHang update(KhachHangRequest khachHangRequest) {
        if (khachHangRequest == null) {
            return null;
        }
        KhachHang existingKhachHang = khachHangRepository.getReferenceById(khachHangRequest.getId());
        if (existingKhachHang == null) {
            return null;
        }
        existingKhachHang.setMa(khachHangRequest.getMa());
        existingKhachHang.setTen(khachHangRequest.getTen());
        existingKhachHang.setSdt(khachHangRequest.getSdt());
        existingKhachHang.setGioiTinh(GioiTinh.valueOf(khachHangRequest.getGioiTinh()));
        existingKhachHang.setNgaySua(LocalDate.now());
        existingKhachHang.setEmail(khachHangRequest.getEmail());
        existingKhachHang.setTrangThai(khachHangRequest.getTrangThai());
        try {
            khachHangRepository.save(existingKhachHang);
            return existingKhachHang;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void remove(Long id) {
        khachHangRepository.deleteById(id);
    }

    @Override
    public KhachHang getById(Long id) {
        Optional<KhachHang> optional = khachHangRepository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return khachHangRepository.existsBySdt(sdt);
    }

    @Override
    public boolean existsBySdtAndIdNot(String sdt, Long id) {
        return khachHangRepository.existsBySdtAndIdNot(sdt, id);
    }

    @Override
    public List<DiaChi> getDiaChiByIdKhachHang(Long idKhachHang) {
        List<DiaChi> list = new ArrayList<>();
        for (DiaChi dc : diaChiRepository.findAll()) {
            if (dc.getKhachHang().getId() == idKhachHang) {
                list.add(dc);
            }
        }
        return list;
    }

    @Override
    public DiaChi getByIdDiaChi(Long idDiaChi) {
        return diaChiRepository.findById(idDiaChi).orElse(null);
    }

    @Override
    public boolean changeUserPassword(Long idKh, String oldPassword, String newPassword) {
        KhachHang khachHang = khachHangRepository.findById(idKh).orElse(null);
//        if (khachHang != null && oldPassword.equals(khachHang.getMatKhau())) {
            khachHang.setMatKhau(newPassword);
            khachHangRepository.save(khachHang);
            return true;
//        }
//        return false;
    }
}
