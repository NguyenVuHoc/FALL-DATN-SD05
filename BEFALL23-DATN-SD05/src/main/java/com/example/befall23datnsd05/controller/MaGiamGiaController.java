package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.KhuyenMaiRequest;
import com.example.befall23datnsd05.dto.MaGiamGiaRequest;
import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.entity.MaGiamGia;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import com.example.befall23datnsd05.service.MaGiamGiaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/ma-giam-gia")
public class MaGiamGiaController {

    @Autowired
    private MaGiamGiaService service;

    List<TrangThaiKhuyenMai> list = new ArrayList<>(Arrays.asList(TrangThaiKhuyenMai.DANG_HOAT_DONG, TrangThaiKhuyenMai.DUNG_HOAT_DONG, TrangThaiKhuyenMai.SAP_DIEN_RA));

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("listMaGiam", service.getAll());
        model.addAttribute("listTrangThai", list);
        return "admin-template/ma_giam_gia/ma_giam_gia";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThaiKhuyenMai trangThaiKhuyenMai) {
        model.addAttribute("listMaGiam", service.getByTrangThai(trangThaiKhuyenMai));
        model.addAttribute("listTrangThai", list);
        return "admin-template/ma_giam_gia/ma_giam_gia";
    }

    @GetMapping("/filter")
    public String filterNgay(Model model,
                             @Param("trangThai") TrangThaiKhuyenMai trangThaiKhuyenMai,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            model.addAttribute("listTrangThai", list);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            return "redirect/admin/ma-giam-gia";
        }
        model.addAttribute("listTrangThai", list);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("listMaGiam", service.findMaGiamGia(startDate, endDate, trangThaiKhuyenMai));
        return "admin-template/ma_giam_gia/ma_giam_gia";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("maGiamGia") MaGiamGiaRequest maGiamGiaRequest,
            Model model
    ) {
        model.addAttribute("maGiamGia", new MaGiamGia());
        return "admin-template/ma_giam_gia/them_ma_giam_gia";
    }

    @PostMapping("/add")
    public String them(
            @Valid
            @ModelAttribute("maGiamGia") MaGiamGiaRequest maGiamGiaRequest,
            BindingResult bindingResult,
            Model model
    ) {
        String ten = maGiamGiaRequest.getTen();
        if (bindingResult.hasErrors()) {
            return "admin-template/ma_giam_gia/them_ma_giam_gia";
        } else {
            if (service.existsByTen(ten)) {
                model.addAttribute("errorTen", "Tên  đã tồn tại");
                return "admin-template/ma_giam_gia/them_ma_giam_gia";
            }
            service.add(maGiamGiaRequest);
            return "redirect:/admin/ma-giam-gia";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("maGiamGia", service.getById(id));
        return "admin-template/ma_giam_gia/sua_ma_giam_gia";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("maGiamGia") MaGiamGiaRequest maGiamGiaRequest,
                         BindingResult bindingResult,
                         Model model) {
        String ten = maGiamGiaRequest.getTen();
        if (bindingResult.hasErrors()) {
            return "admin-template/ma_giam_gia/sua_ma_giam_gia";
        }else {
            if (service.existsByTen(ten)) {
                model.addAttribute("errorTen", "Tên  đã tồn tại");
                return "admin-template/ma_giam_gia/sua_ma_giam_gia";
            }
            service.update(maGiamGiaRequest);
            return "redirect:/admin/ma-giam-gia";
        }

    }

    @GetMapping("/huy/{id}")
    public String huy(@PathVariable("id") Long id) {
        service.huy(id);
        return "redirect:/admin/ma-giam-gia";
    }

}
