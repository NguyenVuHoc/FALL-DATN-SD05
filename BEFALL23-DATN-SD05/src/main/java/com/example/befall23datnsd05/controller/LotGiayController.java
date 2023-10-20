package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.service.LotGiayService;
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
@RequestMapping("/admin/lot-giay")
public class LotGiayController {

    @Autowired
    private LotGiayService service;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(
            Model model
    ){
        Page<LotGiay> page = service.phanTrang(pageNo,5);
        model.addAttribute("listLG",page.stream().toList());
        model.addAttribute("index", pageNo+1);
        return "admin-template/lot_giay/lot_giay";
    }
    @GetMapping("/pre")
    private String pre() {
        pageNo--;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/lot-giay";
    }

    @GetMapping("/next")
    private String next() {
        pageNo++;
        pageNo = service.chuyenPage(pageNo);
        return "redirect:/admin/lot-giay";
    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("lotGiay")LotGiay lotGiay,
            Model model
    ){
        model.addAttribute("lotGiay", new LotGiay());
        return "admin-template/lot_giay/them_lot_giay";
    }

    @PostMapping("/add")
    public String add(
            @Valid
            @ModelAttribute("lotGiay") LotGiay lotGiay,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "admin-template/lot_giay/them_lot_giay";
        }else{
            service.add(lotGiay);
            return "redirect:/admin/lot-giay";
        }
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("lotGiay",service.getById(id));
        return "admin-template/lot_giay/sua_lot_giay";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("lotGiay") LotGiay lotGiay,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "admin-template/lot_giay/sua_lot_giay";
        }
        service.update(lotGiay);
        return "redirect:/admin/lot-giay";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/lot-giay";
    }
}

