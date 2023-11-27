package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.service.LotGiayService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("listLG", service.getAll());
        model.addAttribute("index", pageNo+1);
        return "admin-template/lot_giay/lot_giay";
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
            BindingResult bindingResult,
            Model model
    ){
        String ma = lotGiay.getMa();
        String ten = lotGiay.getTen();
        if(bindingResult.hasErrors()){
            return "admin-template/lot_giay/them_lot_giay";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/lot_giay/them_lot_giay";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            return "admin-template/lot_giay/them_lot_giay";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/lot_giay/them_lot_giay";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(lotGiay);
        return "redirect:/admin/lot-giay?success";

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
        String ten = lotGiay.getTen();
        Long id = lotGiay.getId();
        if (bindingResult.hasErrors()) {
            return "admin-template/lot_giay/sua_lot_giay";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/lot_giay/sua_lot_giay";
        }
        model.addAttribute("success", "Sửa thành công");

        service.update(lotGiay);
        return "redirect:/admin/lot-giay?success";
    }

//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable("id") Long id) {
//        service.remove(id);
//        return "redirect:/admin/lot-giay?success";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        try {
            service.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/lot-giay?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/lot-giay?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/lot-giay?errorMessage";
        }

    }

    @GetMapping("/search")
    public String timTen(@RequestParam("ten") String ten,
                         Model model){
        Page<LotGiay> page = service.timTen(ten, pageNo, 5);
        model.addAttribute("listLG", page.stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/lot_giay/lot_giay";
    }
}

