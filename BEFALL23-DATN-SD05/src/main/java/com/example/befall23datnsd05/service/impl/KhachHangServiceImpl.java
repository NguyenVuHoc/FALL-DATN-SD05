package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.dto.RegisterRequest;
import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.DiaChiRepository;
import com.example.befall23datnsd05.repository.GioHangRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

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
        KhachHang khachHang = new KhachHang();
        khachHang.setMa(khachHangRequest.getMa());
        khachHang.setTen(khachHangRequest.getTen());
        khachHang.setSdt(khachHangRequest.getSdt());
        khachHang.setGioiTinh(GioiTinh.valueOf(khachHangRequest.getGioiTinh()));
        khachHang.setNgayTao(LocalDate.now());
        khachHang.setEmail(khachHangRequest.getEmail());
        khachHang.setMatKhau(khachHangRequest.getMatKhau());
        khachHang.setTrangThai(TrangThai.DANG_HOAT_DONG);
        khachHangRepository.save(khachHang);
        LocalDateTime time = LocalDateTime.now();
        String maGH = "GH" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        GioHang gioHang = new GioHang();
        gioHang.setMa(maGH);
        gioHang.setNgayTao(LocalDate.now());
        gioHang.setTrangThai(TrangThai.DANG_HOAT_DONG);
        gioHang.setKhachHang(khachHang);
        gioHangRepository.save(gioHang);
        return khachHang;
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
    public boolean existsByEmail(String email) {
        return khachHangRepository.existsByEmail(email);
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
    @Override
    public KhachHang registration(RegisterRequest request) {
        return khachHangRepository.save(RegisterRequest.convertToEntity(request));
    }


}
