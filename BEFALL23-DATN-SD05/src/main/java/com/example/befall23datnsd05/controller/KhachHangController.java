package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.DiaChiService;
import com.example.befall23datnsd05.service.KhachHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/khach-hang")
public class KhachHangController {

    @Autowired
    private KhachHangService khachHangService;

    @Autowired
    private DiaChiService diaChiService;

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));


    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("listKH", khachHangService.getList());
        model.addAttribute("trangThais", list);
        model.addAttribute("diaChi", new DiaChiRequest());
        model.addAttribute("listDC", diaChiService.getAll());
        model.addAttribute("index", pageNo + 1);
        return "admin-template/khach_hang/khach_hang";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("listKH", khachHangService.getByTrangThai(trangThai));
        return "admin-template/khach_hang/khach_hang";
    }

    @GetMapping("/view-update/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model) {
        KhachHang khachHang = khachHangService.getById(id);
        model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("diaChi", new DiaChiRequest());
        return "admin-template/khach_hang/sua_khach_hang";
    }

    @GetMapping("/view-add")
    public String viewAdd(Model model) {
        model.addAttribute("khachHang", new KhachHangRequest());
        return "admin-template/khach_hang/them_khach_hang";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("khachHang") KhachHangRequest khachHangRequest,
                      BindingResult bindingResult,
                      Model model) {
        String sdt = khachHangRequest.getSdt();
        if (bindingResult.hasErrors()) {
            return "admin-template/khach_hang/them_khach_hang";
        }
        if (khachHangService.existsBySdt(sdt)) {
            model.addAttribute("errorTen", "Số điện thoại đã tồn tại");
            return "admin-template/khach_hang/them_khach_hang";
        }
        model.addAttribute("success", "Thêm thành công");
        khachHangService.add(khachHangRequest);
        return "redirect:/admin/khach-hang?success";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        khachHangService.remove(id);
        return "redirect:/admin/khach-hang?success";
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachHang") KhachHangRequest khachHangRequest,
                         BindingResult bindingResult,
                         Model model
    ) {
        Long id = khachHangRequest.getId();
        String sdt = khachHangRequest.getSdt();
        if (bindingResult.hasErrors()) {
            return "admin-template/khach_hang/sua_khach_hang";
        }

        if (khachHangService.existsBySdtAndIdNot(sdt, id)) {
            model.addAttribute("errorTen", "Số điện thoại đã tồn tại");
            return "admin-template/khach_hang/sua_khach_hang";
        }
        model.addAttribute("success", "Sửa thành công");
        khachHangService.update(khachHangRequest);
        return "redirect:/admin/khach-hang?success";

    }

    @PostMapping("/add-dia-chi/{idKhachHang}")
    public String addDiaChi(
            @Valid
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,
            @PathVariable("idKhachHang") String idKhachHang
    ) {
        diaChiService.add(diaChiRequest, Long.valueOf(idKhachHang));
        return "redirect:/admin/khach-hang?success";
    }

    @PostMapping("/update-dia-chi/{id}/{idKH}")
    public String updateDiaChi(
            @PathVariable("id") Long id,
            @PathVariable("idKH") Long idKH,
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,
            Model model
    ) {

        diaChiService.update(diaChiRequest, id);
        return "redirect:/admin/khach-hang/view-update/" + idKH;
    }

    @GetMapping("/delete-dia-chi/{id}/{idKH}")
    public String deleteDiaChi(@PathVariable("id") Long id,
                               @PathVariable("idKH") Long idKH

    ) {
        diaChiService.remove(id);
        return "redirect:/admin/khach-hang/view-update/" + idKH;
    }


}
