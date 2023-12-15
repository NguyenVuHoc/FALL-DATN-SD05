package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.service.DeGiayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
//        Page<DeGiay> page = service.phanTrang(pageNo,5);
//        model.addAttribute("listDG",page.stream().toList());
        model.addAttribute("listDG", service.getAll());
        model.addAttribute("index", pageNo+1);
        return "admin-template/de_giay/de_giay";
    }

//    @GetMapping("/pre")
//    private String pre() {
//        pageNo--;
//        pageNo = service.chuyenPage(pageNo);
//        return "redirect:/admin/de-giay";
//    }
//
//    @GetMapping("/next")
//    private String next() {
//        pageNo++;
//        pageNo = service.chuyenPage(pageNo);
//        return "redirect:/admin/de-giay";
//    }

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
            BindingResult bindingResult,
            Model model
    ){
        String ma = deGiay.getMa();
        String ten = deGiay.getTen();
        if(bindingResult.hasErrors()){
            return "admin-template/de_giay/them_de_giay";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/de_giay/them_de_giay";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            return "admin-template/de_giay/them_de_giay";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/de_giay/them_de_giay";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(deGiay);
        return "redirect:/admin/de-giay?success";

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
        String ten = deGiay.getTen();
        Long id = deGiay.getId();
        if (bindingResult.hasErrors()) {
            return "admin-template/de_giay/sua_de_giay";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/de_giay/sua_de_giay";
        }
        model.addAttribute("success", "Cập nhật thành công!");

        service.update(deGiay);
        return "redirect:/admin/de-giay?success";
    }

//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        service.remove(id);
//        return "redirect:/admin/de-giay?success";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        try {
            service.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/de-giay?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/de-giay?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/de-giay?errorMessage";
        }

    }
}

