package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.service.DeGiayService;
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
@RequestMapping("/admin/de-giay")
public class DeGiayController {

    @Autowired
    private DeGiayService service;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(
            Model model
    ){
        Page<DeGiay> page = service.phanTrang(pageNo,5);
        model.addAttribute("listDG",page.stream().toList());
        model.addAttribute("index", pageNo+1);
        return "admin-template/de_giay/de_giay";
    }
    @GetMapping("/pre")
    private String pre() {
        pageNo--;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/de-giay";
    }

    @GetMapping("/next")
    private String next() {
        pageNo++;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/de-giay";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("deGiay")DeGiay deGiay,
            Model model
    ){
        model.addAttribute("deGiay", new DeGiay());
        return "admin-template/de_giay/them_de_giay";
    }

    @PostMapping("/add")
    public String add(
            @Valid
            @ModelAttribute("deGiay") DeGiay deGiay,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin-template/de_giay/them_de_giay";
        }else{
            service.add(deGiay);
            return "redirect:/admin/de-giay";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("deGiay",service.getById(id));
        return "admin-template/de_giay/sua_de_giay";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("deGiay") DeGiay deGiay,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/de_giay/sua_de_giay";
        }
        service.update(deGiay);
        return "redirect:/admin/de-giay";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/de-giay";
    }
}

