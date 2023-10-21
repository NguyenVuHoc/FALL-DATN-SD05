package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.request.ThuongHieuRequest;
import com.example.befall23datnsd05.service.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/thuong-hieu")
public class ThuongHieuController {

    private final ThuongHieuService thuongHieuService;

    public ThuongHieuController(ThuongHieuService thuongHieuService) {
        this.thuongHieuService = thuongHieuService;
    }

    Integer pageNo = 0;

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getPage(pageNo, 5).stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/thuong_hieu/thuong_hieu";
    }

    @GetMapping("/get-trang-thai-hoat-dong")
    public String getAllByActive(Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getPageByActivity(pageNo, 5).stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/thuong_hieu/thuong_hieu";
    }

    @GetMapping("/get-trang-thai-dung-hoat-dong")
    public String getAllByInActive(Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getPageByInActivity(pageNo, 5).stream().toList());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/thuong_hieu/thuong_hieu";
    }

    @GetMapping("/pre")
    public String pre() {
        pageNo--;
        pageNo = thuongHieuService.transferPage(pageNo);
        return "redirect:/admin/thuong-hieu";
    }

    @GetMapping("/next")
    public String next() {
        pageNo++;
        pageNo = thuongHieuService.transferPage(pageNo);
        return "redirect:/admin/thuong-hieu";
    }

    @GetMapping("/view-add-thuong-hieu")
    public String getViewAdd(@ModelAttribute("thuongHieu") ThuongHieuRequest thuongHieu) {
        return "admin-template/thuong_hieu/them_thuong_hieu";
    }

    @PostMapping("/add")
    public String addNew(@Valid @ModelAttribute("thuongHieu") ThuongHieuRequest thuongHieu,
                         BindingResult bindingResult,Model model
    ) {
        String ma = thuongHieu.getMa();
        if (bindingResult.hasErrors()) {
            return "admin-template/thuong_hieu/them_thuong_hieu";
        } else {
            if(thuongHieuService.exist(ma)){
                model.addAttribute("error", "Mã  đã tồn tại");
                return "admin-template/thuong_hieu/them_thuong_hieu";
            }
            thuongHieuService.save(thuongHieu);
            return "redirect:/admin/thuong-hieu";
        }
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id) {
        thuongHieuService.remove(id);
        return "redirect:/admin/thuong-hieu";
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

        if (bindingResult.hasErrors()) {
            return "admin-template/thuong_hieu/sua_thuong_hieu";
        }
        thuongHieuService.update(thuongHieu);
        return "redirect:/admin/thuong-hieu";
    }
}

