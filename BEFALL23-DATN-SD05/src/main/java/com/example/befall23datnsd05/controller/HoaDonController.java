package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/hoa-don")
public class HoaDonController {
    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DA_HUY, TrangThai.DA_HOAN_TRA,
            TrangThai.CHO_XAC_NHAN, TrangThai.HOAN_THANH, TrangThai.DANG_CHUAN_BI, TrangThai.DANG_GIAO));
    @Autowired
    private HoaDonService hoaDonService;

    //    @GetMapping
//    public String getAll(Model model,
//                         @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
//        model.addAttribute("hoadons", hoaDonService.getPage(pageNo).stream().toList());
//        model.addAttribute("trangThais", list);
//        return listByPage(pageNo, model, null, null, null, null);
//    }
//
//
//    @GetMapping("page/{pageNo}")
//    public String listByPage(@PathVariable(name = "pageNo") int pageNo, Model model,
//                             @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
//                             @Param("keyword") String keyword,
//                             @Param("trangThai") TrangThai trangThai) {
//        Page<HoaDon> hoadons = hoaDonService.getByPageAndFilter(pageNo, keyword, startDate, endDate, trangThai);
//
//        model.addAttribute("hoadons", hoadons.stream().toList());
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("pageNo", pageNo);
//        model.addAttribute("index", hoadons.getTotalPages());
//
//        model.addAttribute("startDate", startDate);
//        model.addAttribute("endDate", endDate);
//        model.addAttribute("trangThais", list);
//        model.addAttribute("trangThai", trangThai);
//
//        return "admin-template/hoa_don/hoa_don";
//
//    }
//
//
//    @GetMapping("/trang-thai/{trangThai}")
//    public String getByTrangThai(Model model,
//                                 @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
//                                 @PathVariable("trangThai") TrangThai trangThai) {
//        model.addAttribute("trangThais", list);
//        model.addAttribute("hoadons", hoaDonService.getByTrangThai(trangThai, pageNo, 5));
//        return "admin-template/hoa_don/hoa_don";
//    }
    @GetMapping
    public String getAll(Model model,
                         @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        model.addAttribute("hoadons", hoaDonService.getPage(pageNo).stream().toList());
        model.addAttribute("trangThais", list);
        return listByPage(pageNo, model, null, null, null, null);
    }

    @GetMapping("page/{pageNo}")
    public String listByPage(@PathVariable(name = "pageNo") int pageNo, Model model,
                             @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                             @Param("keyword") String keyword,
                             @Param("trangThai") TrangThai trangThai) {
        Page<HoaDon> hoadons = hoaDonService.getByPageAndFilter(pageNo, keyword, startDate, endDate, trangThai);

        model.addAttribute("hoadons", hoadons.stream().toList());
        model.addAttribute("keyword", keyword);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("index", hoadons.getTotalPages());

        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("trangThais", list);
        model.addAttribute("trangThai", trangThai);

        return "admin-template/hoa_don/hoa_don";
    }


    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("hoadons", hoaDonService.getByTrangThai(trangThai, pageNo, 5));
        return "admin-template/hoa_don/hoa_don";
    }

    @GetMapping("/clear")
    public String clear() {
        return "redirect:/admin/hoa-don";
    }


}
