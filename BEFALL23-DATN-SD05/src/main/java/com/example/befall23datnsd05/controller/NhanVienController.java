package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.service.NhanVienService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    @GetMapping
    public String getAll(Model model) {
        List<NhanVien> listNhanVien = nhanVienService.getAll();
        model.addAttribute("list", listNhanVien);
        model.addAttribute("nhanVien",new NhanVien());
        return "admin-template/nhan_vien/nhan_vien";
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
    public String add(@Valid NhanVien nhanVien, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "admin-template/nhan_vien/them_nhan_vien";
        }else {
            nhanVienService.add(nhanVien);
            return "redirect:/admin/nhan-vien";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        nhanVienService.remove(id);
        return "redirect:/admin/nhan-vien";
    }

    @PostMapping("/update")
    public String update(@Valid NhanVien nhanVien, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "admin-template/nhan_vien/sua_nhan_vien";
        }else {
            nhanVienService.update(nhanVien);
            return "redirect:/admin/nhan-vien";
        }
    }


}
