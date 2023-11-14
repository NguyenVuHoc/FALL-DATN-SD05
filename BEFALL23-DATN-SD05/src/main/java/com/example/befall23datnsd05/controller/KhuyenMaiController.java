package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.KhuyenMaiRequest;
import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.KhuyenMaiService;
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
@RequestMapping("/admin/khuyen-mai")
public class KhuyenMaiController {

    @Autowired
    KhuyenMaiService service;

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG, TrangThai.SAP_DIEN_RA));

    @GetMapping("")
    public String hienThi(Model model){
        model.addAttribute("listKhuyenMai", service.getList());
        model.addAttribute("index", pageNo + 1);
        model.addAttribute("listTrangThai", list);
        return "admin-template/khuyen_mai/khuyen_mai";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("listTrangThai", list);
        model.addAttribute("listKhuyenMai", service.getByTrangThai(trangThai));
        return "admin-template/khuyen_mai/khuyen_mai";
    }

    @GetMapping("/filter")
    public String filterNgay(Model model,
                             @Param("trangThai") TrangThai trangThai,
                             @Param("startDate") LocalDate startDate,
                             @Param("endDate") LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            model.addAttribute("listTrangThai", list);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
            return "redirect/admin/khuyen-mai";
        }
        model.addAttribute("listTrangThai", list);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("listKhuyenMai", service.findKhuyenMai(startDate, endDate, trangThai));
        return "admin-template/khuyen_mai/khuyen_mai";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
            Model model
    ){
        model.addAttribute("khuyenMai", new KhuyenMai());
        return "admin-template/khuyen_mai/them_khuyen_mai";
    }


    @PostMapping("/add")
    public String them(
            @Valid
            @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/khuyen_mai/them_khuyen_mai";
        } else {
            service.add(khuyenMaiRequest);
            return "redirect:/admin/khuyen-mai";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("khuyenMai",service.getById(id));
        return "admin-template/khuyen_mai/sua_khuyen_mai";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khuyenMai") KhuyenMaiRequest khuyenMaiRequest,
                         BindingResult bindingResult ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/khuyen_mai/sua_khuyen_mai";
        }
        service.update(khuyenMaiRequest);
        return "redirect:/admin/khuyen-mai";
    }

    @GetMapping("/huy/{id}")
    public String huyKhuyenMai(@PathVariable("id") Long id) {
        service.huy(id);
        return "redirect:/admin/khuyen-mai";
    }

    @GetMapping("/them-san-pham-khuyen-mai")
    public String sanPhamKhuyenMai(){
        return "admin-template/khuyen_mai/san_pham_khuyen_mai";
    }
}
