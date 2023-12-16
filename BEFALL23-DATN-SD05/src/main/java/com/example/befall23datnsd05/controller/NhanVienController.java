package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.NhanVienRequest;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.NhanVienService;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/nhan-vien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping()
    public String getAll(Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("listNV", nhanVienService.getList());
        model.addAttribute("trangThais", list);
        model.addAttribute("index", pageNo + 1);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/nhan_vien/nhan_vien";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("trangThais", list);
        model.addAttribute("listNV", nhanVienService.getByTrangThai(trangThai));
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/nhan_vien/nhan_vien";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        NhanVien nhanVien = nhanVienService.getById(id);
        model.addAttribute("nhanVien", nhanVien);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/nhan_vien/sua_nhan_vien";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        model.addAttribute("nhanVien", new NhanVien());
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/nhan_vien/them_nhan_vien";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("nhanVien") NhanVienRequest nhanVienRequest,
                      BindingResult bindingResult,
                      Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        String ma = nhanVienRequest.getMa();
        if (bindingResult.hasErrors()){
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/nhan_vien/them_nhan_vien";
        }else {
            if (nhanVienService.existByMa(ma)){
                model.addAttribute("error", "Mã đã tồn tại");
                model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
                return "admin-template/nhan_vien/them_nhan_vien";
            }
            model.addAttribute("success", "Thêm thành công");
            nhanVienService.add(nhanVienRequest);
            return "redirect:/admin/nhan-vien?success";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        try {
            nhanVienService.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/nhan-vien?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/nhan-vien?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/nhan-vien?errorMessage";
        }

    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("nhanVien") NhanVienRequest nhanVienRequest,
                         BindingResult bindingResult,
                         Model model){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null){
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/nhan_vien/sua_nhan_vien";
        }else {
            model.addAttribute("success", "Cập nhật thành công!");
            nhanVienService.update(nhanVienRequest);
            return "redirect:/admin/nhan-vien?success";
        }
    }


}
