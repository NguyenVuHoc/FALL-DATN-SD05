package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.repository.HoaDonRepo;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.request.HoaDonRequest;
import com.example.befall23datnsd05.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HoaDonServiceImpl implements HoaDonService {
    @Autowired
    private HoaDonRepo repository;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<HoaDon> getAll() {
        return repository.findAll();
    }

    @Override
    public List<HoaDon> getAllByKhachHang(Long id) {
        return repository.getAllByKhachHang(id);
    }

    @Override
    public List<HoaDon> getByTrangThai(TrangThaiDonHang trangThai) {
        return repository.getAllByTrangThai(trangThai);
    }

    @Override
    public List<HoaDon> getByTrangThaiAndKhachHang(TrangThaiDonHang trangThai, Long id) {
//        return repository.getAllByTrangThaiAndKhachHang(trangThai.getTrangThai(), id);
        return repository.findHoaDonByTrangThaiAndKhachHangId(trangThai, id);
    }

    @Override
    public HoaDon findById(Long id) {
        Optional<HoaDon> hoaDon = repository.findById(id);
        if (hoaDon.isPresent()) {
            return hoaDon.get();
        }
        return null;
    }

    @Override
    public HoaDon save(HoaDon hoaDon) {
        return repository.save(hoaDon);
    }

    @Override
    public List<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end, TrangThaiDonHang trangThai) {
        return repository.findHoaDonsByNgayTao(start, end);
    }

    @Override
    public boolean validate(HoaDon hoaDon, TrangThaiDonHang trangThai, String newGhiChu) {
        hoaDon.setTrangThai(trangThai);
        hoaDon.setGhiChu(newGhiChu);
        if(hoaDon.getTrangThai()==TrangThaiDonHang.DA_GIAO|| hoaDon.getTrangThai()==TrangThaiDonHang.XAC_NHAN_TRA_HANG|| hoaDon.getTrangThai()==TrangThaiDonHang.DOI_HANG){
            hoaDon.setNgayThanhToan(LocalDate.now());
        }
        hoaDon = repository.save(hoaDon);
        return hoaDon.getTrangThai().equals(trangThai);
    }

    @Override
    public HoaDon createHdHoanTra(HoaDon hoaDon, Long idHd) {
        HoaDon hoaDonNew = new HoaDon();
        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();

        hoaDonNew.setMa(maHD);
        hoaDonNew.setLoaiHoaDon(LoaiHoaDon.HOA_DON_ONLINE);
        hoaDonNew.setTrangThai(TrangThaiDonHang.DA_TRA_HANG);
        hoaDonNew.setKhachHang(repository.findById(idHd).get().getKhachHang());
        hoaDonNew.setGhiChu("Đơn hàng hoàn trả của đơn hàng " + hoaDon.getMa());
        hoaDonNew.setNgayTao(LocalDate.now());
        hoaDonNew.setNgaySua(LocalDate.now());
        hoaDonNew.setSdt(hoaDon.getSdt());
        hoaDonNew.setTenKhachHang(hoaDon.getTenKhachHang());
        hoaDonNew.setNhanVien(hoaDon.getNhanVien());
        repository.save(hoaDonNew);
        return hoaDonNew;
    }

    @Override
    public boolean removeGioHangChiTietHoanTra(GioHangChiTiet gioHangChiTiet, HoaDon hoaDon) {
        if (gioHangChiTiet.getTrangThai().equals(TrangThai.YEU_CAU_TRA_HANG)) {
            gioHangChiTietRepository.delete(gioHangChiTiet);
//            hoaDon.setThanhToan(hoaDon.getThanhToan()- );
        }
        return true;
    }

    @Override
    public GioHangChiTiet createGioHangHoanTraByHoaDon(GioHangChiTiet gioHangChiTiet, HoaDon hoaDon) {
        GioHangChiTiet gioHangChiTietnew = new GioHangChiTiet();

        gioHangChiTietnew.setDonGia(gioHangChiTiet.getDonGia());
        gioHangChiTietnew.setNgayTao(LocalDate.now());
        gioHangChiTietnew.setNgaySua(LocalDate.now());
        gioHangChiTietnew.setGhiChu("sản phẩm hoàn trả của đơn hàng " + hoaDon.getMa());
        gioHangChiTietnew.setSoLuong(gioHangChiTiet.getSoLuong());
        gioHangChiTietnew.setTrangThai(TrangThai.DA_HOAN_TRA);
        gioHangChiTietnew.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
        gioHangChiTietnew.setGioHang(gioHangChiTiet.getGioHang());
        gioHangChiTietnew.setHoaDon(hoaDon);
        return gioHangChiTietRepository.save(gioHangChiTietnew);
    }

    @Override
    public ChiTietSanPham refund(GioHangChiTiet gioHangChiTiet) {
        if (gioHangChiTiet.getTrangThai().equals(TrangThai.YEU_CAU_TRA_HANG)) {
            ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(gioHangChiTiet.getChiTietSanPham().getId()).orElse(null);
            if (chiTietSanPham != null) {
                chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + gioHangChiTiet.getSoLuong());
            }
        }
        return null;
    }


}
