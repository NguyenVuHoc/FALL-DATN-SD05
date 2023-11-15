package com.example.befall23datnsd05.service.Impl;

import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.*;
import com.example.befall23datnsd05.service.BanHangCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;


    @Override
    public void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietSanPhamId).orElse(null);
        GioHang gioHang = gioHangRepository.getByKhachHangId(khachHangId);

        if (gioHang == null){
            gioHang = new GioHang();
            gioHang.setKhachHang(khachHangRepository.findById(khachHangId).orElse(null));
            gioHangRepository.save(gioHang);
        }

        GioHangChiTiet gioHangChiTiet1 = gioHangChiTietRepository.findByGioHangAndChiTietSanPhamAndHoaDonIsNull(gioHang, chiTietSanPham);
        if (gioHangChiTiet1 != null){
            gioHangChiTiet1.setSoLuong(gioHangChiTiet1.getSoLuong() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet1);

        }else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
            gioHangChiTiet.setDonGia(gioHangChiTiet.getChiTietSanPham().getGiaBan());
            gioHangChiTiet.setNgayTao(LocalDate.now());
            gioHangChiTiet.setTrangThai(TrangThai.CHO_XAC_NHAN);
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
    }

    @Override
    public void xoaKhoiGioHang(Long id) {
        gioHangChiTietRepository.deleteById(id);
    }

    @Override
    public void datHang(String ten, String diaChi, String sdt, String ghiChu) {
        KhachHang khachHang = khachHangRepository.findById(Long.valueOf(5)).orElse(null);
        NhanVien nhanVien = nhanVienRepository.findById(Long.valueOf(14)).orElse(null);

        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        BigDecimal totalCost = BigDecimal.ZERO;
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(maHD);
        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setSdt(sdt);
        hoaDon.setDiaChi(diaChi);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTenKhachHang(ten);
        hoaDon.setTrangThai(TrangThai.CHO_XAC_NHAN);
        hoaDon.setLoaiHoaDon(LoaiHoaDon.HOA_DON_ONLINE);
        hoaDonRepository.save(hoaDon);


        List<GioHangChiTiet> list = gioHangChiTietRepository.listGioHangNull();
        for (GioHangChiTiet gh: list){
            gh.setHoaDon(hoaDon);
            gioHangChiTietRepository.save(gh);

            ChiTietSanPham chiTietSanPham = gh.getChiTietSanPham();
            chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() - gh.getSoLuong());
            chiTietSanPhamRepository.save(chiTietSanPham);
            BigDecimal itemCost = chiTietSanPham.getGiaBan().multiply(BigDecimal.valueOf(gh.getSoLuong()));
            totalCost = totalCost.add(itemCost);

        }
        hoaDon.setTongTien(totalCost);
        hoaDonRepository.save(hoaDon);

    }
}
