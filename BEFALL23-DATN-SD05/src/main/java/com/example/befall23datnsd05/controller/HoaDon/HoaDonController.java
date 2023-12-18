package com.example.befall23datnsd05.controller.HoaDon;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.export.HoaDonPDF;
import com.example.befall23datnsd05.importFile.importFileExcelHd;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.service.BanHangService;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import com.example.befall23datnsd05.service.HoaDonChiTietService;
import com.example.befall23datnsd05.service.HoaDonService;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/hoa-don")
public class HoaDonController {
    List<TrangThaiDonHang> list = new ArrayList<>(Arrays.asList(TrangThaiDonHang.CHO_XAC_NHAN, TrangThaiDonHang.HOAN_THANH.DANG_CHUAN_BI,
            TrangThaiDonHang.DANG_GIAO, TrangThaiDonHang.DA_GIAO, TrangThaiDonHang.HOAN_THANH, TrangThaiDonHang.DA_HUY, TrangThaiDonHang.XAC_NHAN_TRA_HANG));
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietService hoaDonChiTietService;
    private final GioHangChiTietService gioHangChiTietService;
    private final ChiTietSanPhamRepository chiTietSanPhamService;
    private  final BanHangService banHangService;
    private final importFileExcelHd importFileExcelHd;

    public HoaDonController(HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService, GioHangChiTietService gioHangChiTietService, ChiTietSanPhamRepository chiTietSanPhamService1, BanHangService banHangService, com.example.befall23datnsd05.importFile.importFileExcelHd importFileExcelHd) {
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
        this.gioHangChiTietService = gioHangChiTietService;
        this.chiTietSanPhamService = chiTietSanPhamService1;
        this.banHangService = banHangService;
        this.importFileExcelHd = importFileExcelHd;
    }

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    /**
     * Get All HoaDon
     *
     * @param model
     * @return
     */
    @GetMapping
    public String getAll(Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("hoadons", hoaDonService.getAll());
        model.addAttribute("trangThais", list);
        model.addAttribute("endDate", LocalDate.now());
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/hoa_don/hoa_don";
    }

    /**
     * Get all HoaDon by trangThai
     *
     * @param model
     * @param trangThai
     * @return
     */
    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThaiDonHang trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        model.addAttribute("trangThais", list);
        model.addAttribute("endDate", LocalDate.now());
        model.addAttribute("hoadons", hoaDonService.getByTrangThai(trangThai));
        return "admin-template/hoa_don/hoa_don";
    }

    /**
     * Filter by Ngay Tao
     *
     * @param model
     * @param trangThai
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping("/filter")
    public String filterNgayTao(Model model,
                                @Param("trangThai") TrangThaiDonHang trangThai,
                                @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        startDate = (startDate != null) ? startDate : LocalDate.of(2000, 1, 1);
        endDate = (endDate != null) ? endDate : LocalDate.now();

        model.addAttribute("trangThais", list);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("hoadons", hoaDonService.findHoaDonsByNgayTao(startDate, endDate));
        return "admin-template/hoa_don/hoa_don";
    }

    /**
     * Clear form
     *
     * @return
     */
    @GetMapping("/clear")
    public String clear() {
        return "redirect:/admin/hoa-don";
    }


    /**
     * get HoaDon offline
     *
     * @param model
     * @param idHd
     * @param trangThai
     * @return
     */
    @GetMapping("/chi-tiet-hoa-don/{id}")
    public String detaiOff(Model model,
                           @PathVariable("id") Long idHd,
                           @Param("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("hoaDon", hoaDonService.findById(idHd));
        model.addAttribute("hdcts", hoaDonChiTietService.getCtspById(idHd));
        model.addAttribute("trangThai", trangThai);
        return "admin-template/hoa_don/chi_tiet_hoa_don";
    }

    /**
     * get HoaDon Online
     *
     * @param model
     * @param idHd
     * @param trangThai
     * @return
     */
    @GetMapping("/chi-tiet-gio-hang/{id}")
    public String detailOn(Model model,
                           @PathVariable("id") Long idHd,
                           @Param("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("hoaDon", hoaDonService.findById(idHd));
        model.addAttribute("ghcts", gioHangChiTietService.findGioHangChiTietById(idHd));
        model.addAttribute("giamGia",hoaDonService.maGiamGia(idHd));
        model.addAttribute("trangThai", trangThai);
        return "admin-template/hoa_don/chi_tiet_hd_online";
    }

    /**
     * Chuyển Trạng Thái
     *
     * @param id
     * @param ghichu
     * @param model
     * @return
     */
    @PostMapping("/validation")
    public String validation(@Param("id") Long id,
                             @RequestParam("ghiChu") String ghichu, Model model
    ) throws Exception {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        int i = 0;
        HoaDon hoaDon = hoaDonService.findById(id);
        if (ghichu.isEmpty()) {
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            model.addAttribute("hoadons", hoaDonService.getAll());
            model.addAttribute("trangThais", list);
            model.addAttribute("endDate", LocalDate.now());
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/hoa_don/hoa_don";
        }
        if (hoaDon.getTrangThai() == TrangThaiDonHang.XAC_NHAN_TRA_HANG || hoaDon.getTrangThai() == TrangThaiDonHang.DOI_HANG|| hoaDon.getTrangThai()==TrangThaiDonHang.DA_HUY) {
            return "redirect:/admin/hoa-don";
        }

        if (hoaDon != null) {
            for (TrangThaiDonHang trangThai : list) {
                if (trangThai.equals(hoaDon.getTrangThai())) {
                    i = list.indexOf(trangThai);
                }
            }
//            trừ số lượng CTSP khi xác nhận đơn hàng
            if (hoaDon.getTrangThai() == TrangThaiDonHang.CHO_XAC_NHAN) {
                for (GioHangChiTiet gioHangChiTiet3 : gioHangChiTietService.findGioHangChiTietById(hoaDon.getId())
                ) {
                    ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(gioHangChiTiet3.getChiTietSanPham().getId()).get();
                    if(chiTietSanPham.getSoLuongTon()< gioHangChiTiet3.getSoLuong()){
                        model.addAttribute("hoadons", hoaDonService.getAll());
                        model.addAttribute("trangThais", list);
                        model.addAttribute("endDate", LocalDate.now());
                        model.addAttribute("err", "Không thành công!Số lượng sản phẩm còn trong kho không đủ!");
                        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
                        return "admin-template/hoa_don/hoa_don";
                    }
                }
                for (GioHangChiTiet gioHangChiTiet3 : gioHangChiTietService.findGioHangChiTietById(hoaDon.getId())
                ) {
                    ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(gioHangChiTiet3.getChiTietSanPham().getId()).get();

                    int soLuongTru = chiTietSanPham.getSoLuongTon() - gioHangChiTiet3.getSoLuong();
                    chiTietSanPham.setSoLuongTon(soLuongTru);
                    chiTietSanPhamService.save(chiTietSanPham);
                }
                HoaDonPDF hoaDonPDF= new HoaDonPDF();
                hoaDonPDF.exportToHdPDF(gioHangChiTietService.findGioHangChiTietById(id),hoaDon);
            }
            if( hoaDon.getTrangThai() == TrangThaiDonHang.DANG_CHUAN_BI){
                HoaDonPDF hoaDonPDF= new HoaDonPDF();
                hoaDonPDF.exportToHdPDF(gioHangChiTietService.findGioHangChiTietById(id),hoaDon);
            }
            hoaDonService.validate(hoaDon, list.get(i + 1), ghichu);
            return "redirect:/admin/hoa-don/trang-thai/"+hoaDon.getTrangThai()+"?success";
        }
        return null;
    }

    /**


    /**
     * Từ chối Trả Hàng+Đổi Hàng
     * >>>>>>> f2418f59d83a165a1d2f5cad4557fe31ec2590a5
     *
     * @param id
     * @param ghichu
     * @param model
     * @return
     */
    @PostMapping("/validation/unaccept")
    public String UnvalidationHoanTra(@Param("id") Long id,
                                      @RequestParam("ghiChu") String ghichu,
                                      Model model
    ) {
        HoaDon hoaDon = hoaDonService.findById(id);
        List<GioHangChiTiet> gioHangChiTiet = gioHangChiTietService.findGioHangChiTietById(id);
        if (hoaDon.getTrangThai() == TrangThaiDonHang.CHO_XAC_NHAN
                || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_CHUAN_BI
                || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO
                || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO
                || hoaDon.getTrangThai() == TrangThaiDonHang.HOAN_THANH
                || hoaDon.getTrangThai() == TrangThaiDonHang.DA_HUY
        ) {
            return "redirect:/admin/hoa-don";
        }
//        if(hoaDon.getTrangThai()!=TrangThaiDonHang.XAC_NHAN_TRA_HANG){
//            return "redirect:/admin/hoa-don";
//        }
        if (hoaDon != null) {
            for (GioHangChiTiet gioHangChiTiet1 : gioHangChiTiet) {
                if (gioHangChiTiet1.getTrangThai() == TrangThai.YEU_CAU_TRA_HANG) {
                    gioHangChiTiet1.setTrangThai(TrangThai.TU_CHOI_TRA_HANG);
                    gioHangChiTietService.save(gioHangChiTiet1);
                }
                if (gioHangChiTiet1.getTrangThai() == TrangThai.DOI_HANG) {
                    gioHangChiTiet1.setTrangThai(TrangThai.TU_CHOI_DOI_HANG);
                    gioHangChiTietService.save(gioHangChiTiet1);
                }
            }
            hoaDonService.validate(hoaDon, TrangThaiDonHang.HOAN_THANH, ghichu);
            return "redirect:/admin/hoa-don/trang-thai/"+hoaDon.getTrangThai()+"?success";
        }
        return null;
    }

    /**
     * Huỷ Đơn Hàng
     *
     * @param id
     * @param ghichu
     * @param model
     * @return
     */
    @PostMapping("/validation/deny")
    public String validationDeny(@Param("id") Long id,
                                 @RequestParam("ghiChu") String ghichu,
                                 Model model
    ) {

        if (ghichu.isEmpty()) {
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            model.addAttribute("hoadons", hoaDonService.getAll());
            model.addAttribute("trangThais", list);
            model.addAttribute("endDate", LocalDate.now());
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/hoa_don/hoa_don";
        }
        HoaDon hoaDon = hoaDonService.findById(id);
        if (hoaDon != null) {
            hoaDonService.validate(hoaDon, TrangThaiDonHang.DA_HUY, ghichu);
            return "redirect:/admin/hoa-don/trang-thai/"+hoaDon.getTrangThai()+"?success";
        }
        return null;
    }

    @PostMapping("/validation/accept-refund")
    public String validationHoanTraVaoKho(@Param("id") Long id,
                                          @RequestParam("ghiChu") String ghichu,
                                          Model model
    ) {
        List<GioHangChiTiet> checkHoaDonHoanTra= new ArrayList<>();
        HoaDon hoaDon = hoaDonService.findById(id);
        BigDecimal tongTienHoanHang = BigDecimal.ZERO;
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietService.findGioHangChiTietById(id);
        if (ghichu.isEmpty()) {
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            model.addAttribute("hoaDon", hoaDonService.findById(id));
            model.addAttribute("ghcts", gioHangChiTietService.findGioHangChiTietById(id));
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/hoa_don/chi_tiet_hd_online";
        }
        if (hoaDon.getTrangThai() == TrangThaiDonHang.CHO_XAC_NHAN || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_CHUAN_BI || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO || hoaDon.getTrangThai() == TrangThaiDonHang.HOAN_THANH || hoaDon.getTrangThai() == TrangThaiDonHang.DA_HUY
        ) {
            return "redirect:/admin/hoa-don";
        }
        if (hoaDon != null) {
            for (GioHangChiTiet gioHangChiTiet1 : gioHangChiTiets) {
                    ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(gioHangChiTiet1.getChiTietSanPham().getId()).get();
                    if (chiTietSanPham.getId() != null && gioHangChiTiet1.getTrangThai() == TrangThai.YEU_CAU_TRA_HANG) {
                        gioHangChiTiet1.setTrangThai(TrangThai.DA_TRA_HANG);
                        gioHangChiTiet1.setGhiChu("sản phẩm hoàn trả");
                        chiTietSanPham.setSoLuongTon(chiTietSanPham.getSoLuongTon() + gioHangChiTiet1.getSoLuong());
                        chiTietSanPhamService.save(chiTietSanPham);
                        BigDecimal giaTriSanPham = gioHangChiTiet1.getDonGia()
                                .multiply(BigDecimal.valueOf(gioHangChiTiet1.getSoLuong()));
                        tongTienHoanHang = tongTienHoanHang.add(giaTriSanPham);
                    }
            }
            if(hoaDon.getThanhToan().compareTo(tongTienHoanHang) < 0){
                hoaDon.setThanhToan(BigDecimal.ZERO);
            }else {
                hoaDon.setThanhToan(hoaDon.getThanhToan().subtract(tongTienHoanHang));
            }
            hoaDon.setNgayThanhToan(LocalDate.now());
            hoaDonService.validate(hoaDon, TrangThaiDonHang.HOAN_THANH, ghichu);
            hoaDonService.save(hoaDon);
            return "redirect:/admin/hoa-don/trang-thai/"+hoaDon.getTrangThai()+"?success";
        }
        return null;
    }

    @PostMapping("/validation/accept")
    public String validationHoanTra(@Param("id") Long id,
                                          @RequestParam("ghiChu") String ghichu,
                                          Model model
    ) {
        HoaDon hoaDon = hoaDonService.findById(id);
        BigDecimal tongTienHoanHang = BigDecimal.ZERO;
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietService.findGioHangChiTietById(id);
        if (ghichu.isEmpty()) {
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            model.addAttribute("hoaDon", hoaDonService.findById(id));
            model.addAttribute("ghcts", gioHangChiTietService.findGioHangChiTietById(id));
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/hoa_don/chi_tiet_hd_online";
        }
        if (hoaDon.getTrangThai() == TrangThaiDonHang.CHO_XAC_NHAN || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_CHUAN_BI || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO || hoaDon.getTrangThai() == TrangThaiDonHang.DANG_GIAO || hoaDon.getTrangThai() == TrangThaiDonHang.HOAN_THANH || hoaDon.getTrangThai() == TrangThaiDonHang.DA_HUY
        ) {
            return "redirect:/admin/hoa-don";
        }
        if (hoaDon != null) {
            for (GioHangChiTiet gioHangChiTiet1 : gioHangChiTiets) {
                ChiTietSanPham chiTietSanPham = chiTietSanPhamService.findById(gioHangChiTiet1.getChiTietSanPham().getId()).get();
                if (chiTietSanPham.getId() != null && gioHangChiTiet1.getTrangThai() == TrangThai.YEU_CAU_TRA_HANG) {
                    gioHangChiTiet1.setTrangThai(TrangThai.DA_TRA_HANG);
                    gioHangChiTiet1.setGhiChu("sản phẩm hoàn trả");
                    chiTietSanPhamService.save(chiTietSanPham);
                    BigDecimal giaTriSanPham = gioHangChiTiet1.getDonGia()
                            .multiply(BigDecimal.valueOf(gioHangChiTiet1.getSoLuong()));
                    tongTienHoanHang = tongTienHoanHang.add(giaTriSanPham);
                }
            }
            if(hoaDon.getThanhToan().compareTo(tongTienHoanHang) < 0){
                hoaDon.setThanhToan(BigDecimal.ZERO);
            }else {
                hoaDon.setThanhToan(hoaDon.getThanhToan().subtract(tongTienHoanHang));
            }
            hoaDon.setNgayThanhToan(LocalDate.now());
            hoaDonService.validate(hoaDon, TrangThaiDonHang.HOAN_THANH, ghichu);
            hoaDonService.save(hoaDon);
            return "redirect:/admin/hoa-don/trang-thai/"+hoaDon.getTrangThai()+"?success";
        }
        return null;
    }



}
