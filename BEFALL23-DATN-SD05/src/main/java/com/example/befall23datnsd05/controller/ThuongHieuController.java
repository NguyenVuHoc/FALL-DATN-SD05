package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.request.ThuongHieuRequest;
import com.example.befall23datnsd05.service.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/thuong-hieu")
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    public ThuongHieuController(ThuongHieuService thuongHieuService) {
        this.thuongHieuService = thuongHieuService;
    }

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("index", pageNo + 1);
        model.addAttribute("trangThais", list);
        return "admin-template/thuong_hieu/thuong_hieu";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("listThuongHieu", thuongHieuService.getByTrangThai(trangThai));
        return "admin-template/thuong_hieu/thuong_hieu";
    }

    @GetMapping("/view-add-thuong-hieu")
    public String getViewAdd(@ModelAttribute("thuongHieu") ThuongHieuRequest thuongHieu) {
        return "admin-template/thuong_hieu/them_thuong_hieu";
    }

    @PostMapping("/add")
    public String addNew(@Valid @ModelAttribute("thuongHieu") ThuongHieuRequest thuongHieu,
                         BindingResult bindingResult, Model model
    ) {
        String ma = thuongHieu.getMa();
        String ten = thuongHieu.getTen();
        if (bindingResult.hasErrors()) {
            return "admin-template/thuong_hieu/them_thuong_hieu";
        }
        if (thuongHieuService.existByMa(ma) && thuongHieuService.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/thuong_hieu/them_thuong_hieu";
        }
        if (thuongHieuService.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            return "admin-template/thuong_hieu/them_thuong_hieu";
        }
        if (thuongHieuService.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            return "admin-template/thuong_hieu/them_thuong_hieu";
        }
        model.addAttribute("success", "Thêm thành công");

        thuongHieuService.save(thuongHieu);
        return "redirect:/admin/thuong-hieu?success";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        thuongHieuService.remove(id);
        return "redirect:/admin/thuong-hieu?success";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id,
                             Model model) {
        model.addAttribute("thuongHieu", thuongHieuService.findById(id));
        return "admin-template/thuong_hieu/sua_thuong_hieu";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("thuongHieu") ThuongHieuRequest thuongHieu,
                         BindingResult bindingResult,
                         Model model
    ) {
        String ten = thuongHieu.getTen();
        Long id = thuongHieu.getId();
        if (bindingResult.hasErrors()) {
            return "admin-template/thuong_hieu/sua_thuong_hieu";
        } else {
            if (thuongHieuService.existsByTenAndIdNot(ten, id)) {
                model.addAttribute("errorTen", "Tên  đã tồn tại");
                return "admin-template/thuong_hieu/sua_thuong_hieu";
            }
            model.addAttribute("success", "Thêm thành công");

            thuongHieuService.update(thuongHieu);
            return "redirect:/admin/thuong-hieu?success";
        }
    }
}

