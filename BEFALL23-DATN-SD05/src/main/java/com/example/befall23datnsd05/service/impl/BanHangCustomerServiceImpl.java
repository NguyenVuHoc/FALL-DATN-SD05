package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.*;
import com.example.befall23datnsd05.sendEmail.SendMailService;
import com.example.befall23datnsd05.service.BanHangCustomerService;
import com.example.befall23datnsd05.wrapper.GioHangWrapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @Autowired
    private DiemTichLuyRepository diemTichLuyRepository;

    @Autowired
    private SendMailService sendMailKhService;


    @Override
    public void themVaoGioHang(Long khachHangId, Long chiTietSanPhamId, Integer soLuong) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(chiTietSanPhamId).orElse(null);
        GioHang gioHang = gioHangRepository.getByKhachHangId(khachHangId);

        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setKhachHang(khachHangRepository.findById(khachHangId).orElse(null));
            gioHangRepository.save(gioHang);
        }

        GioHangChiTiet gioHangChiTiet1 = gioHangChiTietRepository.findByGioHangAndChiTietSanPhamAndHoaDonIsNull(gioHang, chiTietSanPham);
        if (gioHangChiTiet1 != null) {
            gioHangChiTiet1.setSoLuong(gioHangChiTiet1.getSoLuong() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet1);

        } else {
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setSoLuong(soLuong);
            gioHangChiTiet.setChiTietSanPham(chiTietSanPham);
            gioHangChiTiet.setDonGia(gioHangChiTiet.getChiTietSanPham().getGiaBan());
            gioHangChiTiet.setNgayTao(LocalDate.now());
            gioHangChiTiet.setTrangThai(TrangThai.DANG_HOAT_DONG);
            gioHangChiTiet.setGioHang(gioHang);
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
    }

    @Override
    public void xoaKhoiGioHang(Long id) {
        gioHangChiTietRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void datHang(List<GioHangChiTiet> listGioHangChiTiet, String ten, String diaChi, String sdt, String ghiChu) {
        KhachHang khachHang = khachHangRepository.findById(Long.valueOf(5)).orElse(null);
        NhanVien nhanVien = nhanVienRepository.findById(Long.valueOf(14)).orElse(null);

        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        BigDecimal tongTien = BigDecimal.ZERO;
        BigDecimal thanhToan = BigDecimal.ZERO;
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(maHD);
        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setSdt(sdt);
        hoaDon.setDiaChi(diaChi);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTenKhachHang(ten);
        hoaDon.setTrangThai(TrangThaiDonHang.CHO_XAC_NHAN);
        hoaDon.setLoaiHoaDon(LoaiHoaDon.HOA_DON_ONLINE);
        hoaDonRepository.save(hoaDon);

        for (GioHangChiTiet gh : listGioHangChiTiet) {
            gh.setHoaDon(hoaDon);
            gioHangChiTietRepository.save(gh);

            ChiTietSanPham chiTietSanPham = gh.getChiTietSanPham();
            chiTietSanPhamRepository.save(chiTietSanPham);
            BigDecimal itemCost = chiTietSanPham.getGiaBan().multiply(BigDecimal.valueOf(gh.getSoLuong()));
            tongTien = tongTien.add(itemCost);
            BigDecimal itemThanhToan = chiTietSanPham.getGiaBan().multiply(BigDecimal.valueOf(gh.getSoLuong()));
            thanhToan = thanhToan.add(itemThanhToan);
        }

        hoaDon.setTongTien(tongTien);
        hoaDon.setThanhToan(thanhToan);
        hoaDonRepository.save(hoaDon);
    }

    @Override
    public void datHangItems(GioHangWrapper gioHangWrapper, String ten, String diaChi, String sdt, String ghiChu, BigDecimal shippingFee, BigDecimal tongTien, BigDecimal totalAmount, Long selectedVoucherId, BigDecimal diemTichLuy, String useAll) {
        KhachHang khachHang = khachHangRepository.findById(Long.valueOf(5)).orElse(null);
        NhanVien nhanVien = nhanVienRepository.findById(Long.valueOf(14)).orElse(null);
        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(maHD);
        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNhanVien(nhanVien);
        hoaDon.setSdt(sdt);
        hoaDon.setDiaChi(diaChi);
        hoaDon.setGhiChu(ghiChu);
        hoaDon.setTenKhachHang(ten);
        hoaDon.setTrangThai(TrangThaiDonHang.CHO_XAC_NHAN);
        hoaDon.setLoaiHoaDon(LoaiHoaDon.HOA_DON_ONLINE);
        hoaDonRepository.save(hoaDon);
        totalAmount = BigDecimal.valueOf(shippingFee.intValue() + totalAmount.intValue());

        for (GioHangChiTiet gh : gioHangWrapper.getListGioHangChiTiet()) {
            gh.setHoaDon(hoaDon);
            gioHangChiTietRepository.save(gh);
            ChiTietSanPham chiTietSanPham = gh.getChiTietSanPham();
            chiTietSanPhamRepository.save(chiTietSanPham);
        }
        hoaDon.setPhiVanChuyen(shippingFee);
        if (selectedVoucherId == null || selectedVoucherId == 0) {
            hoaDon.setMaGiamGia(null);
        } else {
            MaGiamGia maGiamGia = maGiamGiaRepository.findById(selectedVoucherId).get();
            hoaDon.setMaGiamGia(maGiamGia);
            if (totalAmount.multiply(BigDecimal.valueOf(maGiamGia.getMucGiamGia())).compareTo(maGiamGia.getMucGiamToiDa()) < 0) {
                totalAmount = BigDecimal.valueOf((long) ((100 - maGiamGia.getMucGiamGia()) * totalAmount.intValue()) / 100);
            } else {
                totalAmount = BigDecimal.valueOf((long) (totalAmount.intValue() - maGiamGia.getMucGiamToiDa().intValue()));
            }
            maGiamGia.setSoLuong(maGiamGia.getSoLuong() - 1);
            maGiamGiaRepository.save(maGiamGia);
        }
        if (useAll.equals("false")) {
            hoaDon.setXu(BigDecimal.valueOf(0));
        } else {
            if (diemTichLuy.compareTo(BigDecimal.valueOf(50000)) > 0) {
                hoaDon.setXu(BigDecimal.valueOf(50000));
                totalAmount = BigDecimal.valueOf(totalAmount.intValue() - BigDecimal.valueOf(50000).intValue());
                khachHang.setTichDiem(khachHang.getTichDiem().subtract(BigDecimal.valueOf(50000)));
                khachHangRepository.save(khachHang);
            } else {
                hoaDon.setXu(khachHang.getTichDiem());
                totalAmount = BigDecimal.valueOf(totalAmount.intValue() - khachHang.getTichDiem().intValue());
                khachHang.setTichDiem(BigDecimal.valueOf(0));
                khachHangRepository.save(khachHang);
            }
        }
        hoaDon.setTongTien(tongTien);
        hoaDon.setThanhToan(totalAmount);
        hoaDonRepository.save(hoaDon);
        sendMailKhService.sendEmail1(khachHang, hoaDon);
    }

    @Override
    public List<GioHangChiTiet> updateGioHangChiTiet(Long idGioHangChiTiet, Integer soLuong) {
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
        List<GioHangChiTiet> updatedItems = new ArrayList<>();

        if (optionalGioHangChiTiet.isPresent()) {
            GioHangChiTiet gioHangChiTiet1 = optionalGioHangChiTiet.get();
            gioHangChiTiet1.setSoLuong(soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet1);
            updatedItems.add(gioHangChiTiet1);
        } else {
            throw new EntityNotFoundException("Không tìm thấy GioHangChiTiet với ID: " + idGioHangChiTiet);
        }

        return updatedItems;
    }

    @Override
    public List<GioHangChiTiet> findAllById(List<String> listIdString) {
        List<Long> listIdLong = new ArrayList<>();
        for (String str : listIdString) {
            try {
                Long value = Long.parseLong(str);
                listIdLong.add(value);
            } catch (NumberFormatException e) {
                e.fillInStackTrace();
                // Xử lý ngoại lệ nếu có giá trị không hợp lệ
            }
        }
        return gioHangChiTietRepository.findAllById(listIdLong);
    }

    @Override
    public GioHangWrapper findAllItemsById(List<String> listIdString) {
        List<Long> listIdLong = new ArrayList<>();
        for (String str : listIdString) {
            try {
                Long value = Long.parseLong(str);
                listIdLong.add(value);
            } catch (NumberFormatException e) {
                e.fillInStackTrace();
            }
        }
        GioHangWrapper gioHangWrapper = new GioHangWrapper();
        gioHangWrapper.setListGioHangChiTiet(gioHangChiTietRepository.findAllById(listIdLong));
        return gioHangWrapper;
    }

    @Override
    public Long getIdHoaDonVuaMua(Long idKhachHang) {
        return hoaDonRepository.getIdHoaDon(idKhachHang);
    }
}
