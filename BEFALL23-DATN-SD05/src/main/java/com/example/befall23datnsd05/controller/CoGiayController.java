package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.service.CoGiayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/co-giay")
public class CoGiayController {

    @Autowired
    private CoGiayService service;

    Integer pageNo = 0;

    @GetMapping
    public String getAll(
            Model model
    ){
//        Page<CoGiay> page = service.phanTrang(pageNo,5);
//        model.addAttribute("listCG",page.stream().toList());
        model.addAttribute("listCG", service.getAll());
        model.addAttribute("index", pageNo+1);
        return "admin-template/co_giay/co_giay";
    }
//    @GetMapping("/pre")
//    private String pre() {
//        pageNo--;
//        pageNo = service.chuyenPage(pageNo);
//        return "redirect:/admin/co-giay";
//    }
//
//    @GetMapping("/next")
//    private String next() {
//        pageNo++;
//        pageNo = service.chuyenPage(pageNo);
//        return "redirect:/admin/co-giay";
//    }

    @GetMapping("/view-add")
    public String viewAdd(
            @ModelAttribute("coGiay")CoGiay coGiay,
            Model model
    ){
        model.addAttribute("coGiay", new CoGiay());
        return "admin-template/co_giay/them_co_giay";
    }

    @PostMapping("/add")
    public String add(
            @Valid
            @ModelAttribute("coGiay") CoGiay coGiay,
            BindingResult bindingResult,
            Model model
    ){
        String ma = coGiay.getMa();
        String ten = coGiay.getTen();
        if(bindingResult.hasErrors()){
            return "admin-template/co_giay/them_co_giay";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/co_giay/them_co_giay";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            return "admin-template/co_giay/them_co_giay";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/co_giay/them_co_giay";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(coGiay);
        return "redirect:/admin/co-giay?success";

    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("coGiay",service.getById(id));
        return "admin-template/co_giay/sua_co_giay";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("coGiay") CoGiay coGiay,
                         BindingResult bindingResult,
                         Model model
    ) {
        String ten = coGiay.getTen();
        Long id = coGiay.getId();
        if (bindingResult.hasErrors()) {
            return "admin-template/co_giay/sua_co_giay";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/co_giay/sua_co_giay";
        }
        model.addAttribute("success", "Sửa thành công");
        service.update(coGiay);
        return "redirect:/admin/co-giay?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/co-giay?success";
    }

//    @GetMapping("/search")
//    public String timTen(@RequestParam("ten") String ten,
//                         Model model){
//        Page<CoGiay> page = service.timTen(ten, pageNo, 5);
//        model.addAttribute("listCG", page.stream().toList());
//        model.addAttribute("index", pageNo + 1);
//        return "admin-template/co_giay/co_giay";
//    }
}
