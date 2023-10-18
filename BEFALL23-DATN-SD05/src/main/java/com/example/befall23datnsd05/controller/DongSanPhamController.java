package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.request.DongSanPhamRequest;
import com.example.befall23datnsd05.service.DongSanPhamService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/dong-san-pham")
public class DongSanPhamController {

    private final DongSanPhamService dongSanPhamService;

    public DongSanPhamController(DongSanPhamService dongSanPhamService) {
        this.dongSanPhamService = dongSanPhamService;
    }

    @GetMapping("/hien-thi")
    public String getAll(Model model,
                         @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {
        model.addAttribute("listDongSp", dongSanPhamService.getPage(pageNo,5));
//        model.addAttribute("index", pageNo + 1);
        return "admin-template/dong_san_pham/dong_san_pham";
    }

    @GetMapping("/view-add-dong-san-pham")
    public String getViewAdd(@ModelAttribute("dongSp") DongSanPham dongSanPham) {
        return "admin-template/dong_san_pham/them_dong_san_pham";
    }

    @PostMapping("/add")
    public String addNew(@Valid @ModelAttribute("dongSp") DongSanPhamRequest dongSanPham,
                         BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/dong_san_pham/them_dong_san_pham";
        } else {
            dongSanPhamService.save(dongSanPham);
            return "redirect:/admin/dong-san-pham/hien-thi";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        dongSanPhamService.remove(id);
        return "redirect:/admin/dong-san-pham/hien-thi";
    }

    @GetMapping("view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id,
                             Model model) {
        model.addAttribute("dongSp", dongSanPhamService.findById(id));
        return "admin-template/dong_san_pham/sua_dong_san_pham";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("dongSp") DongSanPhamRequest dongSanPham,
                         BindingResult bindingResult,
                         Model model
    ) {

        if (bindingResult.hasErrors()) {
            return "admin-template/dong_san_pham/sua_dong_san_pham";
        }
        dongSanPhamService.update(dongSanPham);
        return "redirect:/admin/dong-san-pham/hien-thi";
    }
}

