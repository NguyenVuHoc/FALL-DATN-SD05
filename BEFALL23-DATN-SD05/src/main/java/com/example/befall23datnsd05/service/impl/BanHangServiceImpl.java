package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.HoaDonChiTietRepository;
import com.example.befall23datnsd05.repository.HoaDonRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.BanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BanHangServiceImpl implements BanHangService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<HoaDon> getHoaDonCho() {
        List<HoaDon> listHoaDonCho = new ArrayList<>();
        for (HoaDon hoaDon : hoaDonRepository.findAll()) {
            if (hoaDon.getTrangThaiDonHang() == TrangThaiDonHang.HOA_DON_CHO) {
                listHoaDonCho.add(hoaDon);
            }
        }
        return listHoaDonCho;
    }

    @Override
    public List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(Long idHoaDon) {
        List<HoaDonChiTiet> listHDCT = new ArrayList<>();
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon)) {
            if (hoaDonChiTiet.getTrangThaiDonHang() == TrangThaiDonHang.CHO_XAC_NHAN) {
                listHDCT.add(hoaDonChiTiet);
            }
        }
        return listHDCT;
    }

    @Override
    public HoaDon getOneById(Long idHoaDon) {
        return hoaDonRepository.findById(idHoaDon).get();
    }

    @Override
    public ChiTietSanPham getChiTietSanPhamById(Long idChiTietSanPham) {
        return chiTietSanPhamRepository.findById(idChiTietSanPham).get();
    }

    @Override
    public List<ChiTietSanPham> getChiTietSanPham() {
        List<ChiTietSanPham> listChiTietSanPham = new ArrayList<>();
        for (ChiTietSanPham chiTietSanPham : chiTietSanPhamRepository.findAll()) {
            if (chiTietSanPham.getTrangThai() == TrangThai.DANG_HOAT_DONG) {
                listChiTietSanPham.add(chiTietSanPham);
            }
        }
        return listChiTietSanPham;
    }

    @Override
    public HoaDon themHoaDon(HoaDon hoaDon) {
        if (hoaDonRepository.checkHoaDonCho() < 4) {
            for (KhachHang khachHang : khachHangRepository.findAll()) {
                if (khachHang.getMa().equals("KH000")) {
                    hoaDon.setKhachHang(khachHang);
                    return hoaDonRepository.save(hoaDon);
                }
            }
        }
        return null;
    }

    @Override
    public HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham, Long idHoaDon, HoaDonChiTiet hoaDonChiTiet) {
        for (HoaDonChiTiet hdct : hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon)) {
            if (hdct.getChiTietSanPham().getId() == idSanPham) {
                hdct.setSoLuong(hdct.getSoLuong() + hoaDonChiTiet.getSoLuong());
                return hoaDonChiTietRepository.save(hdct);
            }
        }
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet getOneByIdHDCT(Long idHDCT) {
        return hoaDonChiTietRepository.findById(idHDCT).get();
    }

    @Override
    public HoaDonChiTiet xoaHoaDonChiTiet(Long idHoaDonChiTiet) {
        hoaDonChiTietRepository.deleteById(idHoaDonChiTiet);
        return null;
    }


    @Override
    public HoaDon thanhToanHoaDon(Long idHoaDon, String thanhTien) {
        try {
            HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
            if (hoaDon != null) {
                hoaDon.setNgayThanhToan(LocalDate.now());
                hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(thanhTien)));
                hoaDon.setSdt(hoaDon.getKhachHang().getSdt());
                hoaDon.setTenKhachHang(hoaDon.getKhachHang().getTen());
                hoaDon.setTrangThaiDonHang(TrangThaiDonHang.HOAN_THANH);
                return hoaDonRepository.save(hoaDon);
            }
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
        return null;
    }

    @Override
    public BigDecimal getTongTien(Long idHoaDon) {
        BigDecimal thanhTien = BigDecimal.valueOf(0);
        Boolean check = false;
        int index = 0;
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
        while (index < listHDCT.size() && !check) {
            HoaDonChiTiet hoaDonChiTiet = listHDCT.get(index);
            thanhTien = thanhTien.add(hoaDonChiTiet.getGiaBan().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
            index++;

        }
        return thanhTien;
    }

    @Override
    public ChiTietSanPham updateSoLuong(Long idSanPham, Integer soLuong) {
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getChiTietSanPhamById(idSanPham).orElse(null);
        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() - soLuong);
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public ChiTietSanPham updateSoLuongTuHDCT(Long idHDCT) {
        Long idSanPham;
        for (HoaDonChiTiet hoaDonChiTiet : hoaDonChiTietRepository.findAll()) {
            if (hoaDonChiTiet.getId() == idHDCT) {
                idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
                ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getChiTietSanPhamById(idSanPham).orElse(null);
                chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + hoaDonChiTiet.getSoLuong());
                return chiTietSanPhamRepository.save(chiTietSanPham);
            }
        }
        return null;
    }

    @Override
    public HoaDon updateKhachHang(Long idHoaDon, Long idKhachHang) {
        HoaDon hoaDon = hoaDonRepository.getReferenceById(idHoaDon);
        KhachHang khachHang = khachHangRepository.getReferenceById(idKhachHang);
        if (hoaDon != null) {
            hoaDon.setKhachHang(khachHang);
            hoaDonRepository.save(hoaDon);
        }
        return null;
    }

    @Override
    public HoaDonChiTiet tangSoLuongSanPham(Long idHDCT, Integer soLuong) {
        Long idSanPham;
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.getReferenceById(idHDCT);
        hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + soLuong);
        idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.getReferenceById(idSanPham);
        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() - soLuong);
        chiTietSanPhamRepository.save(chiTietSanPham);
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet tangSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong) {
        Long idSanPham;
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idHDCT).orElse(null);
        hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + soLuong);
        idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idSanPham).orElse(null);
        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() - soLuong);
        chiTietSanPhamRepository.save(chiTietSanPham);
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public HoaDonChiTiet giamSoLuongSanPhamHoaDon(Long idHDCT, Integer soLuong) {
        Long idSanPham;
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idHDCT).orElse(null);
        hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() - soLuong);
        idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idSanPham).orElse(null);
        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + soLuong);
        chiTietSanPhamRepository.save(chiTietSanPham);
        if (hoaDonChiTiet.getSoLuong() <= 0) {
            hoaDonChiTietRepository.deleteById(idHDCT);
            return null;
        }
        return hoaDonChiTietRepository.save(hoaDonChiTiet);
    }

    @Override
    public ChiTietSanPham suaSoLuongSanPham(Long idHDCT) {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idHDCT).orElse(null);
        Long idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idSanPham).orElse(null);
        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + hoaDonChiTiet.getSoLuong());
        return chiTietSanPhamRepository.save(chiTietSanPham);
    }

    @Override
    public Boolean huyDon(Long idHoaDon) {
        Boolean check = false;
        int index = 0;
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
        if (!listHDCT.isEmpty()) {
            while (index < listHDCT.size() && !check) {
                HoaDonChiTiet hoaDonChiTiet = listHDCT.get(index);
                Long idSanPham = hoaDonChiTiet.getChiTietSanPham().getId();
                ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idSanPham).get();
                chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + hoaDonChiTiet.getSoLuong());
                hoaDonChiTietRepository.deleteById(hoaDonChiTiet.getId());
                chiTietSanPhamRepository.save(chiTietSanPham);
                index++;

            }
            hoaDonRepository.deleteById(idHoaDon);
            return true;
        } else {
            hoaDonRepository.deleteById(idHoaDon);
            return true;
        }
    }

    @Override
    public KhachHang tichDiem(Long idKhachHang, String thannhTien) {
        BigDecimal tongTien = new BigDecimal(thannhTien);
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).get();
        boolean b = tongTien.compareTo(new BigDecimal("5000000")) >= 0 && tongTien.compareTo(new BigDecimal("9999999")) <= 0;
        boolean b1 = tongTien.compareTo(new BigDecimal("1000000")) >= 0 && tongTien.compareTo(new BigDecimal("4999999")) <= 0;
        boolean b2 = tongTien.compareTo(new BigDecimal("500000")) >= 0 && tongTien.compareTo(new BigDecimal("999999")) <= 0;
        if (khachHang.getMa().equals("KH000")) {
            return null;
        } else if (khachHang.getTichDiem() != null) {
            if (b2) {
                khachHang.setTichDiem(khachHang.getTichDiem().add(BigDecimal.valueOf(Double.valueOf(thannhTien)).multiply(new BigDecimal("0.01"))));
                return khachHangRepository.save(khachHang);
            } else if (b1) {
                khachHang.setTichDiem(khachHang.getTichDiem().add(BigDecimal.valueOf(Double.valueOf(thannhTien)).multiply(new BigDecimal("0.04"))));
                return khachHangRepository.save(khachHang);
            } else if (b) {
                khachHang.setTichDiem(khachHang.getTichDiem().add(BigDecimal.valueOf(Double.valueOf(thannhTien)).multiply(new BigDecimal("0.08"))));
                return khachHangRepository.save(khachHang);
            } else if (tongTien.compareTo(new BigDecimal("10000000")) >= 0) {
                khachHang.setTichDiem(khachHang.getTichDiem().add(BigDecimal.valueOf(Double.valueOf(thannhTien)).multiply(new BigDecimal("0.12"))));
                return khachHangRepository.save(khachHang);
            }
        } else if (khachHang.getTichDiem() == null) {
            if (b2) {
                khachHang.setTichDiem(BigDecimal.valueOf(Double.parseDouble(thannhTien)).multiply(new BigDecimal("0.01")));
                return khachHangRepository.save(khachHang);
            } else if (b1) {
                khachHang.setTichDiem(BigDecimal.valueOf(Double.parseDouble(thannhTien)).multiply(new BigDecimal("0.04")));
                return khachHangRepository.save(khachHang);
            } else if (b) {
                khachHang.setTichDiem(BigDecimal.valueOf(Double.parseDouble(thannhTien)).multiply(new BigDecimal("0.08")));
                return khachHangRepository.save(khachHang);
            } else if (tongTien.compareTo(new BigDecimal("10000000")) >= 0) {
                khachHang.setTichDiem(BigDecimal.valueOf(Double.parseDouble(thannhTien)).multiply(new BigDecimal("0.12")));
                return khachHangRepository.save(khachHang);
            }
        }
        return null;
    }

}
