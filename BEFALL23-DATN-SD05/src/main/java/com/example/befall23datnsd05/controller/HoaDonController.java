package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.HoaDonChiTietService;
import com.example.befall23datnsd05.service.HoaDonService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/hoa-don")
public class HoaDonController {
    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.CHO_XAC_NHAN, TrangThai.DANG_CHUAN_BI,
            TrangThai.DANG_GIAO, TrangThai.HOAN_THANH,TrangThai.XAC_NHAN_HOAN_TRA, TrangThai.DA_HOAN_TRA, TrangThai.DA_HUY));
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietService hoaDonChiTietService;

    public HoaDonController(HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService) {
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
    }


    @GetMapping
    public String getAll(Model model
    ) {
        model.addAttribute("hoadons", hoaDonService.getAll());
        model.addAttribute("trangThais", list);
        return "admin-template/hoa_don/hoa_don";
    }


    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("hoadons", hoaDonService.getByTrangThai(trangThai));
        return "admin-template/hoa_don/hoa_don";
    }

    @GetMapping("/filter")
    public String filterNgayTao(Model model,
                                @Param("trangThai") TrangThai trangThai,
                                @Param("startDate") LocalDate startDate,
                                @Param("endDate") LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            model.addAttribute("trangThais", list);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            return "redirect/amin/hoa-don";

        }
        model.addAttribute("trangThais", list);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("hoadons", hoaDonService.findHoaDonsByNgayTao(startDate, endDate, trangThai));
        return "admin-template/hoa_don/hoa_don";
    }

    @GetMapping("/clear")
    public String clear() {
        return "redirect:/admin/hoa-don";
    }


    @GetMapping("/chi-tiet-hoa-don/{id}")
    public String detail(Model model,
                         @PathVariable("id") Long idHd,
                         @Param("trangThai") TrangThai trangThai) {
        model.addAttribute("hoaDon", hoaDonService.findById(idHd));
        model.addAttribute("hdcts", hoaDonChiTietService.getCtspById(idHd));
        model.addAttribute("trangThai", trangThai);
        return "admin-template/hoa_don/chi_tiet_hoa_don";
    }

    //    @GetMapping("/validation/{id}")
//    public String validation(@PathVariable(name = "id") Long id, Model model, @Param("ghiChu") String ghichu
//    ) {
//        int i = 0;
//        HoaDon hoaDon = hoaDonService.findById(id);
//        if (hoaDon != null) {
//            for (TrangThai trangThai : list) {
//                if (trangThai.equals(hoaDon.getTrangThai())) {
//                    i = list.indexOf(trangThai);
//                }
//            }
//            if (i >= 3) {
//                model.addAttribute("err", "Không thể cập nhật thêm");
//                return "redirect:/admin/hoa-don";
//            }
//            hoaDonService.validate(hoaDon, list.get(i + 1), ghichu);
//            return "redirect:/admin/hoa-don";
//        }
//        return null;
//    }
    @PostMapping("/validation")
    public String validation(@Param("id") Long id, Model model, @RequestParam("ghiChu") String ghichu
    ) {
        int i = 0;
        HoaDon hoaDon = hoaDonService.findById(id);
        if (hoaDon != null) {
            for (TrangThai trangThai : list) {
                if (trangThai.equals(hoaDon.getTrangThai())) {
                    i = list.indexOf(trangThai);
                }
            }
            hoaDonService.validate(hoaDon, list.get(i + 1), ghichu);
            return "redirect:/admin/hoa-don";
        }
        return null;
    }

    @PostMapping("/validation/deny")
    public String validationDeny(@Param("id") Long id, Model model, @RequestParam("ghiChu") String ghichu
    ) {
        int i = 0;
        HoaDon hoaDon = hoaDonService.findById(id);
        if (hoaDon != null) {
            hoaDonService.validate(hoaDon, TrangThai.DA_HUY, ghichu);
            return "redirect:/admin/hoa-don";
        }
        return null;
    }

//    @GetMapping("/validation/deny/{id}")
//    public String validationDaHuy(@PathVariable(name = "id") Long id
//    ) {
//        int i = 0;
//        HoaDon hoaDon = hoaDonService.findById(id);
//        if (hoaDon != null) {
//            hoaDonService.validate(hoaDon, TrangThai.DA_HUY, "hihi");
//            return "redirect:/admin/hoa-don";
//        }
//        return null;
//    }
}
