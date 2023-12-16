package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.service.MauSacService;
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
@RequestMapping("/admin/mau-sac")
public class MauSacController {

    @Autowired
    private MauSacService service;

    Integer pageNo = 0;

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping
    public String getAll(Model model){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("listMS",service.getAll());
        model.addAttribute("index", pageNo+1);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/mau_sac/mau_sac";
    }

    @GetMapping("/view-add")
    public String viewAdd(@ModelAttribute("mauSac")MauSac mauSac,
            Model model){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("mauSac", new MauSac());
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/mau_sac/them_mau_sac";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("mauSac") MauSac mauSac,
            BindingResult bindingResult, Model model){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        String ma = mauSac.getMa();
        String ten = mauSac.getTen();
        if (bindingResult.hasErrors()) {
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existByMa(ma) && service.existsByTen(ten)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existByMa(ma)) {
            model.addAttribute("errorMa", "Mã  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/them_mau_sac";
        }
        if (service.existsByTen(ten)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/them_mau_sac";
        }
        model.addAttribute("success", "Thêm thành công");
        service.add(mauSac);
        return "redirect:/admin/mau-sac?success";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id,
            Model model){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("mauSac",service.getById(id));
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/mau_sac/sua_mau_sac";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("mauSac") MauSac mauSac,
                         BindingResult bindingResult,
                         Model model
    ) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        String ten = mauSac.getTen();
        Long id = mauSac.getId();
        if (bindingResult.hasErrors()) {
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/sua_mau_sac";
        }
        if (service.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("errorTen", "Tên  đã tồn tại");
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/mau_sac/sua_mau_sac";
        }
        model.addAttribute("success", "Cập nhật thành công!");
        service.update(mauSac);
        return "redirect:/admin/mau-sac?success";
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

