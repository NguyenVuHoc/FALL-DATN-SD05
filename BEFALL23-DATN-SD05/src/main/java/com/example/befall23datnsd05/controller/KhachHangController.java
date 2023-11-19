package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(Model model) {
        Page<KhachHang> page = khachHangService.phanTrang(pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/khach_hang/khach_hang";
    }

    @GetMapping("/pre")
    private String pre() {
        pageNo--;
        pageNo = khachHangService.chuyenPage(pageNo);
        return "redirect:/admin/khach-hang";
    }

    @GetMapping("/next")
    private String next() {
        pageNo++;
        pageNo = khachHangService.chuyenPage(pageNo);
        return "redirect:/admin/khach-hang";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        KhachHang khachHang = khachHangService.getById(id);
        model.addAttribute("khachHang", khachHang);
        return "admin-template/khach_hang/sua_khach_hang";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("khachHang", new KhachHang());
        return "admin-template/khach_hang/them_khach_hang";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khachHang") KhachHangRequest khachHangRequest,
                      BindingResult bindingResult,
                      Model model) {
        String ma = khachHangRequest.getMa();
        if (bindingResult.hasErrors()){
            Page<KhachHang> page = khachHangService.phanTrang(pageNo, 5);
            model.addAttribute("list", page.stream().toList());
            model.addAttribute("index", pageNo + 1);
            return "admin-template/khach_hang/them_khach_hang";
        }else {
            if (khachHangService.exist(ma)){
                model.addAttribute("error", "Mã đã tồn tại");
            }
            khachHangService.add(khachHangRequest);
            return "redirect:/admin/khach-hang?success";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        khachHangService.remove(id);
        return "redirect:/admin/khach-hang?success";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachHang") KhachHangRequest khachHangRequest,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin-template/khach_hang/sua_khach_hang";
        }else {
            khachHangService.update(khachHangRequest);
            return "redirect:/admin/khach-hang?success";
        }
    }

    @GetMapping("/search")
    public String timTen(@RequestParam("ten") String ten,
                         Model model){
        Page<KhachHang> page = khachHangService.timTen(ten, pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/khach_hang/khach_hang";
    }

    @GetMapping("/trang-thai-hoat-dong")
    public String getTrangThaiHoatDong(Model model){
        Page<KhachHang> page = khachHangService.getTrangThaiHoatDong(pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/khach_hang/khach_hang";
    }

    @GetMapping("/trang-thai-dung-hoat-dong")
    public String getTrangThaiDungHoatDong(Model model){
        Page<KhachHang> page = khachHangService.getTrangThaiDungHoatDong(pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/khach_hang/khach_hang";
    }
}
