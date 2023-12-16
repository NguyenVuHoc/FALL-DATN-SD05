package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.service.KichThuocService;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping
    public String getAll(Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("listKT", service.getAll());
        model.addAttribute("index", pageNo + 1);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/kich_thuoc/kich_thuoc";
    }

    @GetMapping("/view-add")
    public String viewAdd(@ModelAttribute("kichThuoc") KichThuoc kichThuoc,
                          Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("kichThuoc", new KichThuoc());
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/kich_thuoc/them_kich_thuoc";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("kichThuoc") KichThuoc kichThuoc,
                      BindingResult bindingResult, Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        String ma = kichThuoc.getMa();
        String ten = kichThuoc.getTen();
        if (bindingResult.hasErrors()) {
            return "admin-template/kich_thuoc/them_kich_thuoc";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/kich_thuoc/them_kich_thuoc";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/kich_thuoc/them_kich_thuoc";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/kich_thuoc/them_kich_thuoc";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(kichThuoc);
        return "redirect:/admin/kich-thuoc?success";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("kichThuoc", service.getById(id));
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/kich_thuoc/sua_kich_thuoc";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("kichThuoc") KichThuoc kichThuoc,
                         BindingResult bindingResult,
                         Model model
    ) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        String ten = kichThuoc.getTen();
        Long id = kichThuoc.getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/kich_thuoc/sua_kich_thuoc";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/kich_thuoc/sua_kich_thuoc";
        }
        model.addAttribute("success", "Cập nhật thành công!");
        service.update(kichThuoc);
        return "redirect:/admin/kich-thuoc?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        try {
            service.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/kich-thuoc?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/kich-thuoc?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/kich-thuoc?errorMessage";
        }

    }
}

