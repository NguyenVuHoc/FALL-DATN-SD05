package com.example.befall23datnsd05.controller.HoaDon;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.request.GioHangChiTietRequest;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import com.example.befall23datnsd05.service.HoaDonChiTietService;
import com.example.befall23datnsd05.service.HoaDonService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    public HoaDonUserController(HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService, GioHangChiTietService gioHangChiTietService) {
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
        this.gioHangChiTietService = gioHangChiTietService;
    }

    List<TrangThaiDonHang> list = new ArrayList<>(Arrays.asList(TrangThaiDonHang.CHO_XAC_NHAN, TrangThaiDonHang.HOAN_THANH.DANG_CHUAN_BI,
            TrangThaiDonHang.DANG_GIAO, TrangThaiDonHang.DA_GIAO, TrangThaiDonHang.HOAN_THANH, TrangThaiDonHang.DA_HUY, TrangThaiDonHang.XAC_NHAN_TRA_HANG));

    /**
     * Get HoaDon By KhachHang
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/hoa-don-cua-toi/{idKh}")
    public String getAll(Model model, @PathVariable("idKh") Long id) {
        model.addAttribute("hoadons", hoaDonService.getAllByKhachHang(id));
        model.addAttribute("trangThais", list);
        model.addAttribute("idKh", id.toString());
        return "customer-template/hoadon/hoa_don";
    }

    /**
     * Get HoaDon By KhachHang and TrangThai
     * @param model
     * @param id
     * @param trangThai
     * @return
     */
    @GetMapping("hoa-don-cua-toi/{idKh}/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("idKh") Long id,
                                 @PathVariable("trangThai") TrangThaiDonHang trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("idKh", id.toString());
        model.addAttribute("hoadons", hoaDonService.getByTrangThaiAndKhachHang(trangThai, id));
        return "customer-template/hoadon/hoa_don";
    }

    /**
     * Get ChiTietHoaDon by KhachHang
     * @param model
     * @param idKh
     * @param idHd
     * @return
     */
    @GetMapping("/chi-tiet-hoa-don/{idKh}/{idHd}")
    public String detail(Model model, @PathVariable("idKh") Long idKh,
                         @PathVariable("idHd") Long idHd
    ) {
        model.addAttribute("idKh", idKh.toString());
        model.addAttribute("newGhct", new GioHangChiTietRequest());
        model.addAttribute("hd", hoaDonService.findById(idHd));
        model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
        return "customer-template/hoadon/chi_tiet_hoa_don";
    }

    /**
     * Yêu Cầu Hoàn Trả và số Lượng Sản phẩm hoàn trả
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


        if (gioHangChiTietRequest.getSoLuong()==null) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err","Bạn phải nhập số lượng!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if (gioHangChiTietRequest.getSoLuong() > gioHangChiTiet.getSoLuong()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err","Số lượng trả hàng không thể lớn hơn số lượng mua!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }


        if (gioHangChiTietRequest.getGhiChu().isEmpty()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err","Ghi chú  đơn hàng không được để trống!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if(gioHangChiTiet.getHoaDon().getTrangThai()==TrangThaiDonHang.HOAN_THANH){
            return "redirect:/wingman/chi-tiet-hoa-don/" + gioHangChiTiet.getHoaDon().getKhachHang().getId()+"/"+idHd;
        }
        if (gioHangChiTietRequest.getSoLuong()== gioHangChiTiet.getSoLuong()) {
            hoaDon.setTrangThai(TrangThaiDonHang.XAC_NHAN_TRA_HANG);
            hoaDonService.save(hoaDon);
            gioHangChiTiet.setTrangThai(TrangThai.YEU_CAU_TRA_HANG);
            gioHangChiTietService.save(gioHangChiTiet);
            return "redirect:/wingman/chi-tiet-hoa-don/" + gioHangChiTiet.getHoaDon().getKhachHang().getId()+"/"+idHd+"?success";
        }
        hoaDon.setTrangThai(TrangThaiDonHang.XAC_NHAN_TRA_HANG);
        hoaDonService.save(hoaDon);
        gioHangChiTiet.setSoLuong(gioHangChiTiet.getSoLuong() - gioHangChiTietRequest.getSoLuong());
        gioHangChiTietService.save(gioHangChiTiet);

        GioHangChiTiet gioHangChiTiet1 = new GioHangChiTiet();
        gioHangChiTiet1.setChiTietSanPham(gioHangChiTiet.getChiTietSanPham());
        gioHangChiTiet1.setHoaDon(gioHangChiTiet.getHoaDon());
        gioHangChiTiet1.setGioHang(gioHangChiTiet.getGioHang());
        gioHangChiTiet1.setDonGia(gioHangChiTiet.getDonGia());
        gioHangChiTiet1.setNgayTao(LocalDate.now());
        gioHangChiTiet1.setSoLuong(gioHangChiTietRequest.getSoLuong());
        gioHangChiTiet1.setGhiChu(gioHangChiTietRequest.getGhiChu());
        gioHangChiTiet1.setTrangThai(TrangThai.YEU_CAU_TRA_HANG);
        gioHangChiTietService.save(gioHangChiTiet1);
        return "redirect:/wingman/chi-tiet-hoa-don/" + gioHangChiTiet.getHoaDon().getKhachHang().getId()+"/"+idHd+"?success";
    }



    @PostMapping("/return/{id}/{idHd}")
    public String DoiHang(@Valid @ModelAttribute("newGhct") GioHangChiTietRequest gioHangChiTietRequest,
                          @PathVariable("id") Long idGhct,
                          Model model,
                          @PathVariable("idHd") Long idHd) {
        GioHangChiTiet gioHangChiTiet = gioHangChiTietService.getOne(idGhct).get();
        HoaDon hoaDon = hoaDonService.findById(idHd);
        if (gioHangChiTietRequest.getGhiChu().isEmpty()) {
            model.addAttribute("idKh", gioHangChiTiet.getHoaDon().getKhachHang().getId().toString());
            model.addAttribute("newGhct", new GioHangChiTietRequest());
            model.addAttribute("hd", hoaDonService.findById(idHd));
            model.addAttribute("gioHangChiTiets", gioHangChiTietService.findGioHangChiTietById(idHd));
            model.addAttribute("err","Ghi chú  đơn hàng không được để trống!");
            return "customer-template/hoadon/chi_tiet_hoa_don";
        }

        if(gioHangChiTiet.getHoaDon().getTrangThai()==TrangThaiDonHang.HOAN_THANH){
            return "redirect:/wingman/chi-tiet-hoa-don/" + gioHangChiTiet.getHoaDon().getKhachHang().getId()+"/"+idHd;
        }
        gioHangChiTiet.setTrangThai(TrangThai.DOI_HANG);
        hoaDon.setTrangThai(TrangThaiDonHang.DOI_HANG);
        gioHangChiTietService.save(gioHangChiTiet);
        hoaDonService.save(hoaDon);
        return "redirect:/wingman/chi-tiet-hoa-don/" + gioHangChiTiet.getHoaDon().getKhachHang().getId()+"/"+idHd+"?success";
    }
}
