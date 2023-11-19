package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.service.KichThuocService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/kich-thuoc")
public class KichThuocController {

    @Autowired
    private KichThuocService service;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(
            Model model
    ){
        model.addAttribute("listKT",service.getAll());
        model.addAttribute("index", pageNo+1);
        return "admin-template/kich_thuoc/kich_thuoc";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("kichThuoc")KichThuoc kichThuoc,
            Model model
    ){
        model.addAttribute("kichThuoc", new KichThuoc());
        return "admin-template/kich_thuoc/them_kich_thuoc";
    }

    @PostMapping("/add")
    public String add(
            @Valid
            @ModelAttribute("kichThuoc") KichThuoc kichThuoc,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin-template/kich_thuoc/them_kich_thuoc";
        }else{
            service.add(kichThuoc);
            return "redirect:/admin/kich-thuoc";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("kichThuoc",service.getById(id));
        return "admin-template/kich_thuoc/sua_kich_thuoc";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("kichThuoc") KichThuoc kichThuoc,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/kich_thuoc/sua_kich_thuoc";
        }
        service.update(kichThuoc);
        return "redirect:/admin/kich-thuoc";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/kich-thuoc";
    }
}

