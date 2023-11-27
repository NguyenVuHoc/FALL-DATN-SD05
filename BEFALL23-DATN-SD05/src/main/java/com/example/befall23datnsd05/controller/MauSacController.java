package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.service.MauSacService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("listMS",service.getAll());
        model.addAttribute("index", pageNo+1);
        return "admin-template/mau_sac/mau_sac";
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
            BindingResult bindingResult,
            Model model
    ){
        String ma = mauSac.getMa();
        String ten = mauSac.getTen();
        if (bindingResult.hasErrors()) {
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/mau_sac/them_mau_sac";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(mauSac);
        return "redirect:/admin/mau-sac?success";
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
        String ten = mauSac.getTen();
        Long id = mauSac.getId();
        if (bindingResult.hasErrors()) {
            return "admin-template/mau_sac/sua_mau_sac";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/mau_sac/sua_mau_sac";
        }
        model.addAttribute("success", "Sửa thành công");
        service.update(mauSac);
        return "redirect:/admin/mau-sac?success";
    }
//
//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        service.remove(id);
//        return "redirect:/admin/mau-sac";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        try {
            service.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/mau-sac?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/mau-sac?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/mau-sac?errorMessage";
        }

    }

}

