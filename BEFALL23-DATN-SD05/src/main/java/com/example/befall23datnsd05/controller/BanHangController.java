package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.hoadon.HoaDonRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.BanHangService;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {

    @Autowired
    private BanHangService banHangService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    private Integer page = 0;

    private Integer pageKhachHang = 0;

    private Integer pageChiTietSanPham = 0;

    private Boolean isActive = false;

    @GetMapping()
    public String hienThiBanHang(Model model) {
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("hoaDonCho", new HoaDon());
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        model.addAttribute("index", page);
        model.addAttribute("indexChiTietSP", pageChiTietSanPham);
        return "admin-template/ban_hang/ban_hang";
    }

    @GetMapping("/hoa-don/{idHoaDon}")
    public String hienThiHoaDonChiTiet(@PathVariable("idHoaDon") String idHoaDon,
                                       @RequestParam(name = "pageNo", defaultValue = "0") Integer page,
                                       Model model) {
        if (idHoaDon.isEmpty() == true) {
            model.addAttribute("isActive", isActive);
            return "redirect:/admin/ban-hang";
        } else if (banHangService.getOneById(Long.valueOf(idHoaDon)) != null) {
            isActive = true;
            Page<HoaDonChiTiet> listHDCTPhanTrang = banHangService.getPhanTrang(Long.valueOf(idHoaDon), page, 5);
            model.addAttribute("listHoaDonChiTiet", listHDCTPhanTrang);
            model.addAttribute("index", page + 1);
            model.addAttribute("thanhTien", banHangService.getTongTien(Long.valueOf(idHoaDon)));
            model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
            model.addAttribute("listSanPham", chiTietSanPhamService.phanTrang(pageChiTietSanPham, 5).stream().toList());
            model.addAttribute("indexChiTietSP", pageChiTietSanPham + 1);
            model.addAttribute("hoaDonCho", banHangService.getOneById(Long.valueOf(idHoaDon)));
            model.addAttribute("listKhachHang", khachHangService.phanTrang(pageKhachHang, 5));
            model.addAttribute("indexKhachHang", pageKhachHang + 1);
            model.addAttribute("idHoaDonCho", idHoaDon);
            model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
            model.addAttribute("isActive", isActive);
            return "admin-template/ban_hang/ban_hang";
        }
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDon;
    }

    @GetMapping("/hoa-don/pre/{idHoaDon}")
    public String preHoaDonCho(@PathVariable("idHoaDon") String idHoaDon, Model model) {
        page--;
        page = banHangService.checkPageHDCT(Long.valueOf(idHoaDon), page);
        Page<HoaDonChiTiet> listHDCTPhanTrang = banHangService.getPhanTrang(Long.valueOf(idHoaDon), page, 5);
        model.addAttribute("listHoaDonChiTiet", listHDCTPhanTrang);
        model.addAttribute("index", page + 1);
        model.addAttribute("thanhTien", banHangService.getTongTien(Long.valueOf(idHoaDon)));
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("listSanPham", chiTietSanPhamService.phanTrang(pageChiTietSanPham, 5).stream().toList());
        model.addAttribute("indexChiTietSP", pageChiTietSanPham + 1);
        model.addAttribute("hoaDonCho", banHangService.getOneById(Long.valueOf(idHoaDon)));
        model.addAttribute("listKhachHang", banHangService.getAllKhachHang());
        model.addAttribute("idHoaDonCho", idHoaDon);
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        return "admin-template/ban_hang/ban_hang";
    }

    @GetMapping("/hoa-don/next/{idHoaDon}")
    public String nextHoaDonCho(@PathVariable("idHoaDon") String idHoaDon, Model model) {
        page++;
        page = banHangService.checkPageHDCT(Long.valueOf(idHoaDon), page);
        Page<HoaDonChiTiet> listHDCTPhanTrang = banHangService.getPhanTrang(Long.valueOf(idHoaDon), page, 5);
        model.addAttribute("listHoaDonChiTiet", listHDCTPhanTrang);
        model.addAttribute("index", page + 1);
        model.addAttribute("thanhTien", banHangService.getTongTien(Long.valueOf(idHoaDon)));
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("listSanPham", chiTietSanPhamService.phanTrang(pageChiTietSanPham, 5).stream().toList());
        model.addAttribute("indexChiTietSP", pageChiTietSanPham + 1);
        model.addAttribute("hoaDonCho", banHangService.getOneById(Long.valueOf(idHoaDon)));
        model.addAttribute("listKhachHang", banHangService.getAllKhachHang());
        model.addAttribute("idHoaDonCho", idHoaDon);
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        return "admin-template/ban_hang/ban_hang";
    }

    @GetMapping("/hoa-don/san-pham/pre/{idHoaDonCho}")
    public String preSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho) {
        pageChiTietSanPham--;
        pageChiTietSanPham = chiTietSanPhamService.chuyenPage(pageChiTietSanPham);
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @GetMapping("/hoa-don/san-pham/next/{idHoaDonCho}")
    public String nextSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho) {
        pageChiTietSanPham++;
        pageChiTietSanPham = chiTietSanPhamService.chuyenPage(pageChiTietSanPham);
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @GetMapping("/hoa-don/san-pham/tim-kiem/{idHoaDonCho}")
    public String timKiemSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho,
                                 @RequestParam("ten") String ten,
                                 Model model) {
        model.addAttribute("listSanPham", chiTietSanPhamService.searchTen(ten, pageChiTietSanPham, 5).stream().toList());
        model.addAttribute("indexChiTietSP", pageChiTietSanPham + 1);
        model.addAttribute("listHoaDonChiTiet", banHangService.getPhanTrang(Long.valueOf(idHoaDonCho), page, 5));
        model.addAttribute("index", page + 1);
        model.addAttribute("thanhTien", banHangService.getTongTien(Long.valueOf(idHoaDonCho)));
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("hoaDonCho", banHangService.getOneById(Long.valueOf(idHoaDonCho)));
        model.addAttribute("listKhachHang", banHangService.getAllKhachHang());
        model.addAttribute("idHoaDonCho", idHoaDonCho);
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        return "admin-template/ban_hang/ban_hang";
    }

    @PostMapping("/tao-hoa-don")
    public String taoHoaDon(@ModelAttribute("hoaDon") HoaDon hoaDon,
                            Model model) {
        LocalDateTime time = LocalDateTime.now();
        String maHD = "HD" + String.valueOf(time.getYear()).substring(2) + time.getMonthValue()
                + time.getDayOfMonth() + time.getHour() + time.getMinute() + time.getSecond();
        NhanVien nhanVien = new NhanVien();
        nhanVien.setId(Long.valueOf(4));
        KhachHang khachHang = new KhachHang();
        khachHang.setId(Long.valueOf(5));
        hoaDon = HoaDon.builder()
                .ma(maHD)
                .nhanVien(nhanVien)
                .khachHang(khachHang)
                .ngayTao(LocalDate.now())
                .trangThai(TrangThai.HOA_DON_CHO)
                .build();
        banHangService.themHoaDon(hoaDon);
        model.addAttribute("success", "Thêm thành công");
        return "redirect:/admin/ban-hang?success";
    }

    @PostMapping("/them-khach-hang/{idHoaDon}/{idKhachHang}")
    public String updateKhhachHang(@PathVariable("idHoaDon") String idHoaDon,
                                   @PathVariable("idKhachHang") String idKhachHang) {
        banHangService.updateKhachHang(Long.valueOf(idHoaDon), Long.valueOf(idKhachHang));
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @PostMapping("/them-san-pham/{idHoaDon}/{idSanPham}")
    public String themHoaDonChitiet(@PathVariable("idHoaDon") String idHoaDonCho,
                                    @PathVariable("idSanPham") String idSanPham,
                                    @ModelAttribute("hoaDonChiTiet") HoaDonChiTiet hoaDonChiTiet) {
        if (idHoaDonCho == null) {
            return "redirect:/admin/ban-hang";
        } else if (hoaDonChiTiet.getSoLuong() == null) {
            return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
        }
        HoaDon hoaDon = banHangService.getOneById(Long.valueOf(idHoaDonCho));
        ChiTietSanPham chiTietSanPham = banHangService.getChiTietSanPhamById(Long.valueOf(idSanPham));
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
                .trangThai(TrangThai.CHO_XAC_NHAN)
                .build();
        banHangService.taoHoaDonChiTiet(Long.valueOf(idSanPham), Long.valueOf(idHoaDonCho), hoaDonChiTiet);
        banHangService.updateSoLuong(Long.valueOf(idSanPham), hoaDonChiTiet.getSoLuong());
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @GetMapping("/xoa-hoa-don-chi-tiet/{idHoaDon}/{idHoaDonChiTiet}")
    public String xoaHoaDonChitiet(@PathVariable("idHoaDonChiTiet") String idHoaDonChiTiet) {
        banHangService.xoaHoaDonChiTiet(Long.valueOf(idHoaDonChiTiet));
        banHangService.updateSoLuongTuHDCT(Long.valueOf(idHoaDonChiTiet));
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @PostMapping("/hoa-don/giam-so-luong/{idHoaDonCho}/{idHDCT}")
    public String giamSoLuongSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho,
                                     @PathVariable("idHDCT") String idHDCT,
                                     @RequestParam("soLuong") String soLuong,
                                     Model model) {
        banHangService.giamSoLuongSanPham(Long.valueOf(idHDCT), Integer.valueOf(soLuong));
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @PostMapping("/hoa-don/tang-so-luong/{idHoaDonCho}/{idHDCT}")
    public String tangSoLuongSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho,
                                     @PathVariable("idHDCT") String idHDCT,
                                     @RequestParam("soLuong") String soLuong,
                                     Model model) {
        banHangService.tangSoLuongSanPham(Long.valueOf(idHDCT), Integer.valueOf(soLuong));
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @PostMapping("/thanh-toan")
    public String thanhToanHoaDon(@ModelAttribute("hoaDonCho") HoaDonRequest hoaDonRequest) {
        if (hoaDonRequest == null) {
            return "redirect:/admin/ban-hang";
        }
        banHangService.thanhToanHoaDon(hoaDonRequest);
        return "redirect:/admin/ban-hang";
    }

}
