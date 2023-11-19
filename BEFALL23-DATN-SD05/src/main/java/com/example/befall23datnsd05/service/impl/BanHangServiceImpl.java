//package com.example.befall23datnsd05.service.impl;
//
//import com.example.befall23datnsd05.dto.hoadon.HoaDonCustom;
//import com.example.befall23datnsd05.dto.hoadon.HoaDonRequest;
//import com.example.befall23datnsd05.dto.hoadonchitiet.HoaDonChiTietCustom;
//import com.example.befall23datnsd05.entity.ChiTietSanPham;
//import com.example.befall23datnsd05.entity.HoaDon;
//import com.example.befall23datnsd05.entity.HoaDonChiTiet;
//import com.example.befall23datnsd05.entity.KhachHang;
//import com.example.befall23datnsd05.enumeration.TrangThai;
//import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
//import com.example.befall23datnsd05.repository.HoaDonChiTietRepository;
//import com.example.befall23datnsd05.repository.HoaDonRepository;
//import com.example.befall23datnsd05.repository.KhachHangRepository;
//import com.example.befall23datnsd05.service.BanHangService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BanHangServiceImpl implements BanHangService {
//
//    @Autowired
//    private HoaDonRepository hoaDonRepository;
//
//    @Autowired
//    private HoaDonChiTietRepository hoaDonChiTietRepository;
//
//    @Autowired
//    private ChiTietSanPhamRepository chiTietSanPhamRepository;
//
//    @Autowired
//    private KhachHangRepository khachHangRepository;
//
//    @Override
//    public List<HoaDon> getHoaDonCho() {
//        List<HoaDon> listHoaDonCho = new ArrayList<>();
//        for (HoaDon hoaDon : hoaDonRepository.findAll()) {
//            if (hoaDon.getTrangThai() == TrangThai.HOA_DON_CHO) {
//                listHoaDonCho.add(hoaDon);
//            }
//        }
//        return listHoaDonCho;
//    }
//
//    @Override
//    public List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(Long idHoaDon) {
//        return hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
//    }
//
//    @Override
//    public HoaDonCustom getHoaDonById(Long idHoaDon) {
//        return hoaDonRepository.getHoaDonById(idHoaDon);
//    }
//
//    @Override
//    public HoaDon getOneById(Long idHoaDon) {
//        return hoaDonRepository.getReferenceById(idHoaDon);
////        if (hoaDon.isPresent()){
////            return hoaDon.get();
////        }
////        return null;
//    }
//
//    @Override
//    public ChiTietSanPham getChiTietSanPhamById(Long idChiTietSanPham) {
//        return chiTietSanPhamRepository.getReferenceById(idChiTietSanPham);
//    }
//
//    @Override
//    public List<ChiTietSanPham> getChiTietSanPham() {
//        List<ChiTietSanPham> listChiTietSanPham = new ArrayList<>();
//        for (ChiTietSanPham chiTietSanPham : chiTietSanPhamRepository.findAll()) {
//            if (chiTietSanPham.getTrangThai() == TrangThai.DUNG_HOAT_DONG) {
//                listChiTietSanPham.add(chiTietSanPham);
//            }
//        }
//        return listChiTietSanPham;
//    }
//
//    @Override
//    public HoaDon themHoaDon(HoaDon hoaDon) {
//        if (hoaDonRepository.checkHoaDonCho() < 4) {
//            return hoaDonRepository.save(hoaDon);
//        }
//        return null;
//    }
//
//    @Override
//    public HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham, Long idHoaDon, HoaDonChiTiet hoaDonChiTiet) {
//        for (HoaDonChiTiet hdct : hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon)) {
//            if (hdct.getChiTietSanPham().getId() == idSanPham) {
//                hdct.setSoLuong(hdct.getSoLuong() + hoaDonChiTiet.getSoLuong());
//                return hoaDonChiTietRepository.save(hdct);
//            }
//        }
//        return hoaDonChiTietRepository.save(hoaDonChiTiet);
//    }
//
//    @Override
//    public HoaDonChiTiet xoaHoaDonChiTiet(Long idHoaDonChiTiet) {
//        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.getReferenceById(idHoaDonChiTiet);
//        hoaDonChiTiet.setTrangThai(TrangThai.DUNG_HOAT_DONG);
//        return hoaDonChiTietRepository.save(hoaDonChiTiet);
//    }
//
//    @Override
//    public List<HoaDonChiTietCustom> getOneHDCTByHD(Long idHoaDon) {
//        return hoaDonChiTietRepository.getOneHDCTByHD(idHoaDon);
//    }
//
//    @Override
//    public HoaDon thanhToanHoaDon(HoaDonRequest hoaDonRequest) {
//        HoaDon hoaDon = hoaDonRepository.getReferenceById(Long.valueOf(hoaDonRequest.getId()));
//        if (hoaDon != null) {
//            hoaDon.setId(Long.valueOf(hoaDon.getId()));
//            hoaDon.setNgayThanhToan(LocalDate.now());
//            hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(hoaDonRequest.getThanhToan())));
//            hoaDon.setTenKhachHang(hoaDon.getKhachHang().getTen());
//            hoaDon.setTrangThai(TrangThai.HOAN_THANH);
//            return hoaDonRepository.save(hoaDon);
//        }
//        return null;
//    }
//
//    @Override
//    public BigDecimal getTongTien(Long idHoaDon) {
//        BigDecimal thanhTien = BigDecimal.valueOf(0);
//        Boolean check = false;
//        int index = 0;
//        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
//        while (index < listHDCT.size() && !check) {
//            HoaDonChiTiet hoaDonChiTiet = listHDCT.get(index);
//            thanhTien = thanhTien.add(hoaDonChiTiet.getGiaBan().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
//            index++;
//
//        }
//        return thanhTien;
//    }
//
//    @Override
//    public Page<HoaDonChiTiet> getPhanTrang(Long idHoaDon, Integer pageNo, Integer size) {
//        Pageable pageable = PageRequest.of(pageNo, size);
//        return hoaDonChiTietRepository.getPhanTrang(pageable, idHoaDon);
//    }
//
//    @Override
//    public Integer checkPageHDCT(Long idHoaDon, Integer pageNo) {
//        Integer sizeList = hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon).size();
//        Integer pageCount = (int) Math.ceil((double) sizeList / 5);
//        if (pageNo >= pageCount) {
//            pageNo = 0;
//        } else if (pageNo < 0) {
//            pageNo = pageCount - 1;
//        }
//        return pageNo;
//    }
//
//    @Override
//    public ChiTietSanPham updateSoLuong(Long idSanPham, Integer soLuong) {
//        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getChiTietSanPhamById(idSanPham).orElse(null);
//        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() - soLuong);
//        return chiTietSanPhamRepository.save(chiTietSanPham);
//    }
//
//    @Override
//    public ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT) {
//        Long idSanPham;
//        for (HoaDonChiTiet hoaDonChiTiet: hoaDonChiTietRepository.findAll()){
//            if (hoaDonChiTiet.getId() == idHDCT){
//                idSanPham =  hoaDonChiTiet.getChiTietSanPham().getId();
//                ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getChiTietSanPhamById(idSanPham).orElse(null);
//                chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + hoaDonChiTiet.getSoLuong());
//                return chiTietSanPhamRepository.save(chiTietSanPham);
//            }
//        }
//        return null;
//    }
//
//    @Override
//    public List<KhachHang> getAllKhachHang() {
//        return khachHangRepository.findAll();
//    }
//
//    @Override
//    public HoaDon updateKhachHang(Long idHoaDon, Long idKhachHang) {
//        HoaDon hoaDon = hoaDonRepository.getReferenceById(idHoaDon);
//        KhachHang khachHang = khachHangRepository.getReferenceById(idKhachHang);
//        if (hoaDon != null){
//            hoaDon.setKhachHang(khachHang);
//            hoaDonRepository.save(hoaDon);
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//
//        BanHangService banHangService = new BanHangServiceImpl();
//        BigDecimal thanhTien = banHangService.getTongTien(Long.valueOf(20));
//        System.out.println(thanhTien);
//    }
//}
