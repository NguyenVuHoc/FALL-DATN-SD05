package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.service.MauSacService;
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

@Controller
@RequestMapping("/admin/mau-sac")
public class MauSacController {

    @Autowired
    private MauSacService service;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(
            Model model
    ){
        Page<MauSac> page = service.phanTrang(pageNo,5);
        model.addAttribute("listMS",page.stream().toList());
        model.addAttribute("index", pageNo+1);
        return "admin-template/mau_sac/mau_sac";
    }
    @GetMapping("/pre")
    private String pre() {
        pageNo--;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/mau-sac";
    }

    @GetMapping("/next")
    private String next() {
        pageNo++;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/mau-sac";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("mauSac")MauSac mauSac,
            Model model
    ){
        model.addAttribute("mauSac", new MauSac());
        return "admin-template/mau_sac/them_mau_sac";
    }

    @PostMapping("/add")
    public String add(
            @Valid
            @ModelAttribute("mauSac") MauSac mauSac,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin-template/mau_sac/them_mau_sac";
        }else{
            service.add(mauSac);
            return "redirect:/admin/mau-sac";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("mauSac",service.getById(id));
        return "admin-template/mau_sac/sua_mau_sac";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("mauSac") MauSac mauSac,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/mau_sac/sua_mau_sac";
        }
        service.update(mauSac);
        return "redirect:/admin/mau-sac";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/mau-sac";
    }
}

