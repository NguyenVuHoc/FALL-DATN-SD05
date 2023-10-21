package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.NhanVienRequest;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(Model model) {
        Page<NhanVien> page = nhanVienService.phanTrang(pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/nhan_vien/nhan_vien";
    }

    @GetMapping("/pre")
    private String pre() {
        pageNo--;
        pageNo = nhanVienService.chuyenPage(pageNo);
        return "redirect:/admin/nhan-vien";
    }

    @GetMapping("/next")
    private String next() {
        pageNo++;
        pageNo = nhanVienService.chuyenPage(pageNo);
        return "redirect:/admin/nhan-vien";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        NhanVien nhanVien = nhanVienService.getById(id);
        model.addAttribute("nhanVien", nhanVien);
        return "admin-template/nhan_vien/sua_nhan_vien";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("nhanVien", new NhanVien());
        return "admin-template/nhan_vien/them_nhan_vien";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanVien") NhanVienRequest nhanVienRequest,
                      BindingResult bindingResult,
                      Model model) {
        String ma = nhanVienRequest.getMa();
        if (bindingResult.hasErrors()){
            Page<NhanVien> page = nhanVienService.phanTrang(pageNo, 5);
            model.addAttribute("list", page.stream().toList());
            model.addAttribute("index", pageNo + 1);
            return "admin-template/nhan_vien/them_nhan_vien";
        }else {
            if (nhanVienService.exist(ma)){
                model.addAttribute("error", "Mã đã tồn tại");
            }
            nhanVienService.add(nhanVienRequest);
            return "redirect:/admin/nhan-vien";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        nhanVienService.remove(id);
        return "redirect:/admin/nhan-vien";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanVien") NhanVienRequest nhanVienRequest,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin-template/nhan_vien/sua_nhan_vien";
        }else {
            nhanVienService.update(nhanVienRequest);
            return "redirect:/admin/nhan-vien";
        }
    }

    @GetMapping("/search")
    public String timTen(@RequestParam("ten") String ten,
                         Model model){
        Page<NhanVien> page = nhanVienService.timTen(ten, pageNo, 5);
        model.addAttribute("list", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/nhan_vien/nhan_vien";
    }
}
