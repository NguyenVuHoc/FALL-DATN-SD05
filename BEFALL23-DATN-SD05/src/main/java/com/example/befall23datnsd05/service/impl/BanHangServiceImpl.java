package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.MaGiamGia;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.HoaDonChiTietRepository;
import com.example.befall23datnsd05.repository.HoaDonRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.repository.MaGiamGiaRepository;
import com.example.befall23datnsd05.repository.NhanVienRepository;
import com.example.befall23datnsd05.service.BanHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Override
    public List<HoaDon> getHoaDonCho() {
        List<HoaDon> listHoaDonCho = new ArrayList<>();
        for (HoaDon hoaDon : hoaDonRepository.findAll()) {
            if (hoaDon.getTrangThai() == TrangThaiDonHang.HOA_DON_CHO) {
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
    public HoaDon themHoaDon(HoaDon hoaDon,Long idNhanVien) {
        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        if (hoaDonRepository.checkHoaDonCho() < 4) {
            for (KhachHang khachHang : khachHangRepository.findAll()) {
                if (khachHang.getMa().equals("KH000")) {
                    NhanVien nhanVien = nhanVienRepository.findById(idNhanVien).get();
                    hoaDon = HoaDon.builder()
                            .ma(maHD)
                            .nhanVien(nhanVien)
                            .khachHang(khachHang)
                            .ngayTao(LocalDate.now())
                            .loaiHoaDon(LoaiHoaDon.HOA_DON_OFFLINE)
                            .trangThai(TrangThaiDonHang.HOA_DON_CHO)
                            .build();
                    return hoaDonRepository.save(hoaDon);
                }
            }
        }
        return null;
    }

    @Override
    public HoaDonChiTiet taoHoaDonChiTiet(Long idSanPham, Long idHoaDon, HoaDonChiTiet hoaDonChiTiet) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        ChiTietSanPham chiTietSanPham = chiTietSanPhamRepository.findById(idSanPham).get();
        hoaDonChiTiet = HoaDonChiTiet.builder()
                .hoaDon(hoaDon)
                .chiTietSanPham(chiTietSanPham)
                .deGiay(chiTietSanPham.getDeGiay().getTen())
                .kichThuoc(chiTietSanPham.getKichThuoc().getTen())
                .mauSac(chiTietSanPham.getMauSac().getTen())
                .tenSanPham(chiTietSanPham.getSanPham().getTen())
                .ngayTao(LocalDate.now())
                .giaBan(chiTietSanPham.getGiaBan())
                .soLuong(hoaDonChiTiet.getSoLuong())
                .trangThaiDonHang(TrangThaiDonHang.CHO_XAC_NHAN)
                .build();
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
    public HoaDon thanhToanHoaDon(Long idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        hoaDon.setNgayThanhToan(LocalDate.now());
        hoaDon.setSdt(hoaDon.getKhachHang().getSdt());
        hoaDon.setPhiVanChuyen(BigDecimal.ZERO);
        hoaDon.setTenKhachHang(hoaDon.getKhachHang().getTen());
        hoaDon.setTrangThai(TrangThaiDonHang.HOAN_THANH);
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public HoaDon checkXuHoaDon(Long idHoaDon, String tongTien, String thanhTien, Boolean xuTichDiem) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        KhachHang khachHang = khachHangRepository.findById(hoaDon.getKhachHang().getId()).get();
        if (xuTichDiem == true) {
            if (hoaDon.getKhachHang().getTichDiem() == null) {
                hoaDon.setTongTien(BigDecimal.valueOf(Double.valueOf(tongTien)));
                hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(thanhTien)));
                return hoaDonRepository.save(hoaDon);
            } else if (hoaDon.getKhachHang().getTichDiem().compareTo(new BigDecimal("50000")) < 0) {
                hoaDon.setXu(khachHang.getTichDiem());
                hoaDon.setTongTien(BigDecimal.valueOf(Double.valueOf(tongTien)));
                hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(thanhTien)).subtract(hoaDon.getKhachHang().getTichDiem()));
                khachHang.setTichDiem(new BigDecimal(0));
                khachHangRepository.save(khachHang);
                return hoaDonRepository.save(hoaDon);
            }
            hoaDon.setTongTien(BigDecimal.valueOf(Double.valueOf(tongTien)));
            hoaDon.setXu(new BigDecimal("50000"));
            hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(thanhTien)).subtract(new BigDecimal("50000")));
            khachHang.setTichDiem(khachHang.getTichDiem().subtract(new BigDecimal("50000")));
            khachHangRepository.save(khachHang);
            return hoaDonRepository.save(hoaDon);
        }
        hoaDon.setTongTien(BigDecimal.valueOf(Double.valueOf(tongTien)));
        hoaDon.setThanhToan(BigDecimal.valueOf(Double.valueOf(thanhTien)));
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public HoaDon themGiamGia(Long idHoaDon, Long idGiamGia, BigDecimal tongTien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getKhachHang().getMa().equals("KH000")) {
            return null;
        }
        MaGiamGia maGiamGia = maGiamGiaRepository.findById(idGiamGia).get();
        if (tongTien.compareTo(maGiamGia.getGiaTriDonHang()) >= 0) {
            hoaDon.setMaGiamGia(maGiamGia);
            return hoaDonRepository.save(hoaDon);
        }
        return null;
    }

    @Override
    public MaGiamGia updateGiamGia(Long idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        try {
            if (hoaDon != null) {
                MaGiamGia maGiamGia = maGiamGiaRepository.findById(hoaDon.getMaGiamGia().getId()).get();
                maGiamGia.setSoLuong(maGiamGia.getSoLuong() - 1);
                return maGiamGiaRepository.save(maGiamGia);
            }
        } catch (NullPointerException nullPointerException) {
            return null;
        }
        return null;
    }

    @Override
    public HoaDon huyGiamGia(Long idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getMaGiamGia() != null) {
            hoaDon.setMaGiamGia(null);
            hoaDonRepository.save(hoaDon);
        }
        return null;
    }

    @Override
    public BigDecimal voucher(Long idHoaDon, BigDecimal tongTien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getMaGiamGia() == null) {
            return new BigDecimal(0);
        } else {
            if (hoaDon.getMaGiamGia().getSoLuong() <= 0) {
                return new BigDecimal(0);
            } else {
                if (hoaDon.getMaGiamGia().getMucGiamToiDa().compareTo(tongTien.divide(BigDecimal.valueOf(hoaDon.getMaGiamGia().getMucGiamGia()))) > 0) {
                    return tongTien.divide(BigDecimal.valueOf(hoaDon.getMaGiamGia().getMucGiamGia()));
                }
                return hoaDon.getMaGiamGia().getMucGiamToiDa();
            }
        }
    }

    @Override
    public Integer checkVoucher(Long idHoaDon, Long idMaGiamGia, BigDecimal tongTien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        MaGiamGia maGiamGia = maGiamGiaRepository.findById(idMaGiamGia).get();
        if (hoaDon.getKhachHang().getMa().equals("KH000")) {
            return 1;
        } else if (tongTien.compareTo(maGiamGia.getGiaTriDonHang()) < 0) {
            return 2;
        }
        return 0;
    }

    @Override
    public BigDecimal getTongTien(Long idHoaDon) {
        BigDecimal tongTien = BigDecimal.valueOf(0);
        Boolean check = false;
        int index = 0;
        List<HoaDonChiTiet> listHDCT = hoaDonChiTietRepository.getHoaDonChiTietByIdHoaDon(idHoaDon);
        while (index < listHDCT.size() && !check) {
            HoaDonChiTiet hoaDonChiTiet = listHDCT.get(index);
            tongTien = tongTien.add(hoaDonChiTiet.getGiaBan().multiply(BigDecimal.valueOf(hoaDonChiTiet.getSoLuong())));
            index++;

        }
        return tongTien;
    }

    @Override
    public BigDecimal getThanhTien(Long idHoaDon, BigDecimal tongTien) {
        BigDecimal thanhTien;
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getMaGiamGia() == null) {
            thanhTien = tongTien;
        } else if (tongTien.divide(BigDecimal.valueOf(hoaDon.getMaGiamGia().getMucGiamGia())).compareTo(hoaDon.getMaGiamGia().getMucGiamToiDa()) < 0) {
            thanhTien = tongTien.subtract(tongTien.divide(BigDecimal.valueOf(hoaDon.getMaGiamGia().getMucGiamGia())));
        } else {
            thanhTien = tongTien.subtract(hoaDon.getMaGiamGia().getMucGiamToiDa());
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
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).get();
        if (hoaDon != null) {
            hoaDon.setKhachHang(khachHang);
            hoaDonRepository.save(hoaDon);
        }
        return null;
    }

    @Override
    public HoaDon checkGiamGia(Long idHoaDon, BigDecimal tongTien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getMaGiamGia() == null){
            return null;
        }
        if (hoaDon.getMaGiamGia().getGiaTriDonHang().compareTo(tongTien) > 0) {
            hoaDon.setMaGiamGia(null);
            return hoaDonRepository.save(hoaDon);
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
    public KhachHang tichDiem(Long idKhachHang, String thanhTien) {
        KhachHang khachHang = khachHangRepository.findById(idKhachHang).get();
        if (khachHang.getMa().equals("KH000")) {
            return null;
        } else if (khachHang.getTichDiem() != null) {
            khachHang.setTichDiem(khachHang.getTichDiem().add(BigDecimal.valueOf(Double.valueOf(thanhTien)).multiply(new BigDecimal("0.01"))));
            return khachHangRepository.save(khachHang);
        } else if (khachHang.getTichDiem() == null) {
            khachHang.setTichDiem(BigDecimal.valueOf(Double.parseDouble(thanhTien)).multiply(new BigDecimal("0.01")));
            return khachHangRepository.save(khachHang);
        }
        return null;
    }

}
