package com.example.befall23datnsd05.controller.banhang;

import com.example.befall23datnsd05.dto.hoadon.HoaDonRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.export.HoaDonPDF;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.service.BanHangService;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.KhachHangService;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.List;

@Controller
@RequestMapping("/admin/ban-hang")
public class BanHangController {

    @Autowired
    private BanHangService banHangService;

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    private Boolean isActive = false;

    private Boolean checkHoaDon = false;

    @GetMapping()
    public String hienThiBanHang(Model model) {
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("hoaDonCho", new HoaDon());
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        model.addAttribute("checkHoaDon", checkHoaDon);
        return "admin-template/ban_hang/ban_hang";
    }

    @GetMapping("/hoa-don/{idHoaDon}")
    public String hienThiHoaDonChiTiet(@PathVariable("idHoaDon") String idHoaDon,
                                       Model model) {
        isActive = true;
        HoaDon hoaDon = banHangService.getOneById(Long.valueOf(idHoaDon));
        model.addAttribute("listHoaDonChiTiet", banHangService.getHoaDonChiTietByIdHoaDon(Long.valueOf(idHoaDon)));
        model.addAttribute("thanhTien", banHangService.getTongTien(Long.valueOf(idHoaDon)));
        model.addAttribute("listHoaDonCho", banHangService.getHoaDonCho());
        model.addAttribute("listSanPham", chiTietSanPhamService.getAll());
        model.addAttribute("hoaDonCho", banHangService.getOneById(Long.valueOf(idHoaDon)));
        model.addAttribute("listKhachHang", khachHangRepository.findAll());
        model.addAttribute("idHoaDonCho", idHoaDon);
        model.addAttribute("hoaDonChiTiet", new HoaDonChiTiet());
        model.addAttribute("isActive", isActive);
        model.addAttribute("checkHoaDon", checkHoaDon == true);
        model.addAttribute("xu", hoaDon.getKhachHang().getTichDiem());
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
                .sdt(khachHang.getSdt())
                .tenKhachHang(khachHang.getTen())
                .khachHang(khachHang)
                .ngayTao(LocalDate.now())
                .loaiHoaDon(LoaiHoaDon.HOA_DON_OFFLINE)
                .trangThaiDonHang(TrangThaiDonHang.HOA_DON_CHO)
                .build();
        banHangService.themHoaDon(hoaDon);
        model.addAttribute("success", "Thêm thành công");
        return "redirect:/admin/ban-hang?success";
    }

    @PostMapping("/hoa-don/{idHoaDon}/them-khach-hang/{idKhachHang}")
    public String updateKhhachHang(@PathVariable("idHoaDon") String idHoaDon,
                                   @PathVariable("idKhachHang") String idKhachHang) {
        banHangService.updateKhachHang(Long.valueOf(idHoaDon), Long.valueOf(idKhachHang));
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @PostMapping("/hoa-don/{idHoaDon}/them-san-pham/{idSanPham}")
    public String themHoaDonChitiet(@PathVariable("idHoaDon") String idHoaDonCho,
                                    @PathVariable("idSanPham") String idSanPham,
                                    @ModelAttribute("hoaDonChiTiet") HoaDonChiTiet hoaDonChiTiet) {
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
                .trangThaiDonHang(TrangThaiDonHang.CHO_XAC_NHAN)
                .build();
        banHangService.taoHoaDonChiTiet(Long.valueOf(idSanPham), Long.valueOf(idHoaDonCho), hoaDonChiTiet);
        banHangService.updateSoLuong(Long.valueOf(idSanPham), hoaDonChiTiet.getSoLuong());
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @GetMapping("/hoa-don/{idHoaDon}/xoa-hoa-don-chi-tiet/{idHoaDonChiTiet}")
    public String xoaHoaDonChitiet(@PathVariable("idHoaDonChiTiet") String idHoaDonChiTiet) {
        banHangService.updateSoLuongTuHDCT(Long.valueOf(idHoaDonChiTiet));
        banHangService.xoaHoaDonChiTiet(Long.valueOf(idHoaDonChiTiet));
        return "redirect:/admin/ban-hang/hoa-don/{idHoaDon}";
    }

    @PostMapping("/hoa-don/{idHoaDonCho}/tang-so-luong/{idHDCT}")
    public String tangSoLuongSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho,
                                     @PathVariable("idHDCT") String idHDCT,
                                     @RequestParam("soLuong") String soLuong) {
        banHangService.tangSoLuongSanPhamHoaDon(Long.valueOf(idHDCT), Integer.valueOf(soLuong));
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @PostMapping("/hoa-don/{idHoaDonCho}/giam-so-luong/{idHDCT}")
    public String giamSoLuongSanPham(@PathVariable("idHoaDonCho") String idHoaDonCho,
                                     @PathVariable("idHDCT") String idHDCT,
                                     @RequestParam("soLuong") String soLuong) {
        banHangService.giamSoLuongSanPhamHoaDon(Long.valueOf(idHDCT), Integer.valueOf(soLuong));
        return "redirect:/admin/ban-hang/hoa-don/" + idHoaDonCho;
    }

    @PostMapping("/thanh-toan/{idHoaDonCho}")
    public String thanhToanHoaDon(@PathVariable("idHoaDonCho") String idHoaDon,
                                  @RequestParam("thanhTien") String thanhTien) {
        HoaDon hoaDon = banHangService.getOneById(Long.valueOf(idHoaDon));
        banHangService.thanhToanHoaDon(Long.valueOf(idHoaDon), thanhTien);
        banHangService.tichDiem(hoaDon.getKhachHang().getId(), thanhTien);
        return "redirect:/admin/ban-hang";
    }

    @PostMapping ("/hoa-don/xuat-hoan-don/{idHoaDonCho}")
    public String xuatHoaDon(@PathVariable("idHoaDonCho") String idHoaDon,
                             @RequestParam("thanhTien") String thanhTien) throws Exception {
        banHangService.thanhToanHoaDon(Long.valueOf(idHoaDon), thanhTien);
        HoaDon hoaDon = banHangService.getOneById(Long.valueOf(idHoaDon));
        //Xuat hoa don
        List<HoaDonChiTiet> listHDCT = banHangService.getHoaDonChiTietByIdHoaDon(Long.valueOf(idHoaDon));
        HoaDonPDF hoaDonPDF = new HoaDonPDF();
        hoaDonPDF.exportToPDF(listHDCT, hoaDon);
        return "redirect:/admin/ban-hang";
    }

    @GetMapping("/hoa-don/{idHoaDonCho}/huy-don")
    public String huyDon(@PathVariable("idHoaDonCho") String idHoaDon){
        banHangService.huyDon(Long.valueOf(idHoaDon));
        return "redirect:/admin/ban-hang";
    }

}
