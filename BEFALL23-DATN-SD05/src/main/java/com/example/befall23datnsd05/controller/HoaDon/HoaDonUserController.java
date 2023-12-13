package com.example.befall23datnsd05.controller.HoaDon;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.request.GioHangChiTietRequest;
import com.example.befall23datnsd05.service.*;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/wingman")
public class HoaDonUserController {
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietService hoaDonChiTietService;
    private final GioHangChiTietService gioHangChiTietService;
    private final ChiTietSanPhamService chiTietSanPhamService;
    private final KhachHangService khachHangService;
    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();
    public HoaDonUserController(HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService, GioHangChiTietService gioHangChiTietService, ChiTietSanPhamService chiTietSanPhamService, KhachHangService khachHangService) {
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
        this.gioHangChiTietService = gioHangChiTietService;
        this.chiTietSanPhamService = chiTietSanPhamService;
        this.khachHangService = khachHangService;
    }

    List<TrangThaiDonHang> list = new ArrayList<>(Arrays.asList(TrangThaiDonHang.CHO_XAC_NHAN, TrangThaiDonHang.HOAN_THANH.DANG_CHUAN_BI,
            TrangThaiDonHang.DANG_GIAO, TrangThaiDonHang.DA_GIAO, TrangThaiDonHang.HOAN_THANH, TrangThaiDonHang.DA_HUY, TrangThaiDonHang.XAC_NHAN_TRA_HANG));

    /**
     * Get HoaDon By KhachHang
     *
     * @param model
     * @return
     */
    @GetMapping("/hoa-don-cua-toi")
    public String getAll(Model model ){

        Long id=principalKhachHang.getCurrentUserId();
        if(id==null){
            return "redirect:/login";
        }
        model.addAttribute("hoadons", hoaDonService.getAllByKhachHang(id));
        model.addAttribute("trangThais", list);
        return "customer-template/hoadon/hoa_don";
    }

    /**
     * Get HoaDon By KhachHang and TrangThai
     *
     * @param model
     * @param trangThai
     * @return
     */
    @GetMapping("hoa-don-cua-toi/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThaiDonHang trangThai) {
        Long id=principalKhachHang.getCurrentUserId();
        if(id==null){
            return "redirect:/login";
        }
        model.addAttribute("trangThais", list);
        model.addAttribute("hoadons", hoaDonService.getByTrangThaiAndKhachHang(trangThai, id));
        return "customer-template/hoadon/hoa_don";
    }

    /**
     * Get ChiTietHoaDon by KhachHang
     *
     * @param model
     * @param idHd
     * @return
     */
    @GetMapping("/chi-tiet-hoa-don/{idHd}")
    public String detail(Model model,
                         @PathVariable("idHd") Long idHd
    ) {
        Long id=principalKhachHang.getCurrentUserId();
        if(id==null){
            return "redirect:/login";
        }
        model.addAttribute("giamGia",hoaDonService.maGiamGia(idHd));
        model.addAttribute("newGhct", new GioHangChiTietRequest());
        model.addAttribute("hd", hoaDonService.findById(idHd));
        model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
        return "customer-template/hoadon/chi_tiet_hoa_don";
    }

    /**
     * Yêu Cầu Hoàn Trả và số Lượng Sản phẩm hoàn trả
     *
     * @param gioHangChiTietRequest
     * @param idGhct
     * @param idHd
     * @return
     */
    @PostMapping("/put/{id}/{idHd}")
    public String hoanTra(@Valid @ModelAttribute("newGhct") GioHangChiTietRequest gioHangChiTietRequest,
                          Model model,
                          @PathVariable("id") Long idGhct,
                          @PathVariable("idHd") Long idHd) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getOne(idGhct).get();
        HoaDon hoaDon = hoaDonService.findById(idHd);
        Long id=principalKhachHang.getCurrentUserId();
        if(id==null){
            return "redirect:/login";
        }

        if (gioHangChiTietRequest.getSoLuong() == null) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err", "Bạn phải nhập số lượng!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if (gioHangChiTietRequest.getSoLuong() > gioHangChiTiet.getSoLuong()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err", "Số lượng trả hàng không thể lớn hơn số lượng mua!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }


        if (gioHangChiTietRequest.getGhiChu().isEmpty()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if (gioHangChiTiet.getHoaDon().getTrangThai() == TrangThaiDonHang.HOAN_THANH) {
            return "redirect:/wingman/chi-tiet-hoa-don/" + idHd;
        }
        if (gioHangChiTietRequest.getSoLuong() == gioHangChiTiet.getSoLuong()) {
            hoaDon.setTrangThai(TrangThaiDonHang.XAC_NHAN_TRA_HANG);
            hoaDonService.save(hoaDon);
            gioHangChiTiet.setTrangThai(TrangThai.YEU_CAU_TRA_HANG);
            gioHangChiTietService.save(gioHangChiTiet);
            return "redirect:/wingman/chi-tiet-hoa-don/"+ idHd + "?success";
        }
        hoaDon.setTrangThai(TrangThaiDonHang.XAC_NHAN_TRA_HANG);
        hoaDonService.save(hoaDon);
        gioHangChiTiet.setSoLuong(gioHangChiTiet.getSoLuong() - gioHangChiTietRequest.getSoLuong());
        gioHangChiTietService.save(gioHangChiTiet);

        GioHangChiTiet gioHangChiTiet1 = new GioHangChiTiet();
        gioHangChiTiet1.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
        gioHangChiTiet1.setHoaDon(gioHangChiTiet.getHoaDon());
        gioHangChiTiet1.setGioHang(gioHangChiTiet.getGioHang());
        gioHangChiTiet1.setDonGia(gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSoLuong())));
        gioHangChiTiet1.setNgayTao(LocalDate.now());
        gioHangChiTiet1.setSoLuong(gioHangChiTietRequest.getSoLuong());
        gioHangChiTiet1.setGhiChu(gioHangChiTietRequest.getGhiChu());
        gioHangChiTiet1.setTrangThai(TrangThai.YEU_CAU_TRA_HANG);
        for (GioHangChiTiet gioHangChiTiet2: gioHangChiTietService.findGioHangChiTietById(idHd)){
            if (gioHangChiTiet2.getTrangThai().equals(TrangThai.YEU_CAU_TRA_HANG)
                    && gioHangChiTiet.getChiTietSanPham().getId().equals(gioHangChiTiet2.getChiTietSanPham().getId())) {
                int tongSoLuong = gioHangChiTiet2.getSoLuong() + gioHangChiTietRequest.getSoLuong();
                gioHangChiTiet2.setSoLuong(tongSoLuong);
                gioHangChiTiet2.setGhiChu(gioHangChiTietRequest.getGhiChu());
                gioHangChiTietService.save(gioHangChiTiet2);
                return "redirect:/wingman/chi-tiet-hoa-don/" + idHd + "?success";
            }
        }
            gioHangChiTietService.save(gioHangChiTiet1);
        return "redirect:/wingman/chi-tiet-hoa-don/"  + idHd + "?success";
    }


    @PostMapping("/return/{id}/{idHd}")
    public String DoiHang(@Valid @ModelAttribute("newGhct") GioHangChiTietRequest gioHangChiTietRequest,
                          @PathVariable("id") Long idGhct,
                          Model model,
                          @PathVariable("idHd") Long idHd) {
        Long id=principalKhachHang.getCurrentUserId();
        if(id==null){
            return "redirect:/login";
        }
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getOne(idGhct).get();
        HoaDon hoaDon = hoaDonService.findById(idHd);
        if (gioHangChiTietRequest.getGhiChu().isEmpty()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if (gioHangChiTiet.getHoaDon().getTrangThai() == TrangThaiDonHang.HOAN_THANH) {
            return "redirect:/wingman/chi-tiet-hoa-don/"+ idHd;
        }
        gioHangChiTiet.setTrangThai(TrangThai.DOI_HANG);
        hoaDon.setTrangThai(TrangThaiDonHang.DOI_HANG);
        gioHangChiTietService.save(gioHangChiTiet);
        hoaDonService.save(hoaDon);
        return "redirect:/wingman/chi-tiet-hoa-don/"  + idHd + "?success";
    }

    @PostMapping("/validation/deny")
    public String validationDeny(@Param("id") Long id,
                                 @RequestParam("ghiChu") String ghichu,
                                 Model model
    ) {
        Long idKh=principalKhachHang.getCurrentUserId();
        if(idKh==null){
            return "redirect:/login";
        }
        if (ghichu.isEmpty()) {
            model.addAttribute("err", "Ghi chú  đơn hàng không được để trống!");
            model.addAttribute("hoadons", hoaDonService.getAll());
            model.addAttribute("trangThais", list);
            return "redirect:/wingman/chi-tiet-hoa-don/"  + id + "?success";
        }
        HoaDon hoaDon = hoaDonService.findById(id);
        if (hoaDon != null) {
            if (hoaDon.getTrangThai() != TrangThaiDonHang.CHO_XAC_NHAN) {
                for (GioHangChiTiet gioHangChiTiet3 : gioHangChiTietService.findGioHangChiTietById(hoaDon.getId())
                ) {
                    ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(gioHangChiTiet3.getChiTietSanPham().getId());
                    int soLuongHoanKho = chiTietSanPham.getSoLuongTon() + gioHangChiTiet3.getSoLuong();
                    chiTietSanPham.setSoLuongTon(soLuongHoanKho);
                    chiTietSanPhamService.save(chiTietSanPham);
                }
            }
            hoaDonService.validate(hoaDon, TrangThaiDonHang.DA_HUY, ghichu);
            return "redirect:/wingman/chi-tiet-hoa-don/"  + id + "?success";
        }
        return null;
    }

    @PostMapping("/validation")
    public String validation(@Param("id") Long id
    ) {
        Long idKh = principalKhachHang.getCurrentUserId();
        if (idKh == null) {
            return "redirect:/login";
        }
        HoaDon hoaDon = hoaDonService.findById(id);
        if (hoaDon != null) {
                hoaDonService.validate(hoaDon, TrangThaiDonHang.HOAN_THANH, "");
                return "redirect:/wingman/chi-tiet-hoa-don/" + id + "?success";
        }
        return "redirect:/wingman/chi-tiet-hoa-don/" + id + "?success";
    }

}
