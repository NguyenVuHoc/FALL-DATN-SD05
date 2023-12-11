package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.importFile.FileExcelCTSP;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.CoGiayService;
import com.example.befall23datnsd05.service.DeGiayService;
import com.example.befall23datnsd05.service.KichThuocService;
import com.example.befall23datnsd05.service.LotGiayService;
import com.example.befall23datnsd05.service.MauSacService;
import com.example.befall23datnsd05.service.SanPhamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ChiTietSanPhamController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private ChiTietSanPhamService service;

    @Autowired
    private DeGiayService deGiayService;

    @Autowired
    private MauSacService mauSacService;

    @Autowired
    private KichThuocService kichThuocService;

    @Autowired
    private LotGiayService lotGiayService;

    @Autowired
    private CoGiayService coGiayService;

    @Autowired
    FileExcelCTSP importFileExcelCTSP;

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));

    @GetMapping("/admin/chi-tiet-san-pham")
    public String getAll(
            Model model
    ){
        model.addAttribute("listCTSP",service.getAll());
        model.addAttribute("listDG",deGiayService.getAll());
        model.addAttribute("listMS",mauSacService.getAll());
        model.addAttribute("listKT",kichThuocService.getAll());
        model.addAttribute("listLG",lotGiayService.getAll());
        model.addAttribute("listCG",coGiayService.getAll());
        model.addAttribute("listSP",sanPhamService.getList());
        model.addAttribute("trangThais", list);
        model.addAttribute("index", pageNo+1);
        return "admin-template/chi_tiet_san_pham/chi_tiet_san_pham";
    }

    @GetMapping("/admin/chi-tiet-san-pham/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("listCTSP", service.getByTrangThai(trangThai));
        return "admin-template/chi_tiet_san_pham/chi_tiet_san_pham";
    }

    @GetMapping("/admin/chi-tiet-san-pham/view-add")
    public String viewAdd(
            @ModelAttribute("chiTietSanPham")ChiTietSanPham chiTietSanPham,
            Model model
    ){
        model.addAttribute("listDG",deGiayService.getAll());
        model.addAttribute("listMS",mauSacService.getAll());
        model.addAttribute("listKT",kichThuocService.getAll());
        model.addAttribute("listLG",lotGiayService.getAll());
        model.addAttribute("listCG",coGiayService.getAll());
        model.addAttribute("listSP",sanPhamService.getList());

        model.addAttribute("chiTietSanPham", new ChiTietSanPham());
        return "admin-template/chi_tiet_san_pham/them_chi_tiet_san_pham";
    }

    @PostMapping("/admin/chi-tiet-san-pham/add")
    public String add(
            @Valid
            @ModelAttribute("chiTietSanPham") ChiTietSanPhamRequest chiTietSanPham,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            model.addAttribute("listDG",deGiayService.getAll());
            model.addAttribute("listMS",mauSacService.getAll());
            model.addAttribute("listKT",kichThuocService.getAll());
            model.addAttribute("listLG",lotGiayService.getAll());
            model.addAttribute("listCG",coGiayService.getAll());
            model.addAttribute("listSP",sanPhamService.getList());

            return "admin-template/chi_tiet_san_pham/them_chi_tiet_san_pham";
        }else{
            service.add(chiTietSanPham);
            return "redirect:/admin/chi-tiet-san-pham?success";
        }
    }

    @GetMapping("/admin/chi-tiet-san-pham/view-update/{id}")
    public String viewUpdate(
            @PathVariable("id") Long id,
            Model model
    ){
        model.addAttribute("listDG",deGiayService.getAll());
        model.addAttribute("listMS",mauSacService.getAll());
        model.addAttribute("listKT",kichThuocService.getAll());
        model.addAttribute("listLG",lotGiayService.getAll());
        model.addAttribute("listCG",coGiayService.getAll());
        model.addAttribute("listSP",sanPhamService.getList());

        model.addAttribute("chiTietSanPham",service.getById(id));
        return "admin-template/chi_tiet_san_pham/sua_chi_tiet_san_pham";
    }

    @PostMapping("/admin/chi-tiet-san-pham/update")
    public String update(@Valid @ModelAttribute("chiTietSanPham") ChiTietSanPhamRequest chiTietSanPham,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listDG",deGiayService.getAll());
            model.addAttribute("listMS",mauSacService.getAll());
            model.addAttribute("listKT",kichThuocService.getAll());
            model.addAttribute("listLG",lotGiayService.getAll());
            model.addAttribute("listCG",coGiayService.getAll());
            model.addAttribute("listSP",sanPhamService.getList());

            return "admin-template/chi_tiet_san_pham/sua_chi_tiet_san_pham";
        }
        service.update(chiTietSanPham);
        return "redirect:/admin/chi-tiet-san-pham?success";
    }

    @GetMapping("/admin/chi-tiet-san-pham/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        service.remove(id);
        return "redirect:/admin/chi-tiet-san-pham?success";
    }

    @PostMapping("/admin/chi-tiet-san-pham/import-excel")
    public ResponseEntity<?> importExcel(@ModelAttribute("file") MultipartFile file) throws Exception {
        try {
            System.out.println(file.getName());
            importFileExcelCTSP.ImportFile(file);
            return ResponseEntity.ok(new Exception("success"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/admin/chi-tiet-san-pham/export-excel")
    public ResponseEntity<?> exportExcel(){
        return importFileExcelCTSP.createExcel();
    }

    @GetMapping("/admin/chi-tiet-san-pham/export-excel-mau")
    public ResponseEntity<?> exportExcelMau(){
        return importFileExcelCTSP.createExcelTemplate();
    }

}
