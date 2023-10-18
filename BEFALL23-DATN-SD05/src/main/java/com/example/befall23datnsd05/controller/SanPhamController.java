package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.UploadFile.FileUploadUtil;
import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.request.SanPhamRequest;
import com.example.befall23datnsd05.service.AnhSanPhamService;
import com.example.befall23datnsd05.service.DongSanPhamService;
import com.example.befall23datnsd05.service.SanPhamService;
import com.example.befall23datnsd05.service.ThuongHieuService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/san-pham")
public class SanPhamController {
    private final DongSanPhamService dongSanPhamService;
    private final ThuongHieuService thuongHieuService;
    private final AnhSanPhamService anhSanPhamService;
    private final SanPhamService sanPhamService;

    public SanPhamController(DongSanPhamService dongSanPhamService, ThuongHieuService thuongHieuService, AnhSanPhamService anhSanPhamService, SanPhamService sanPhamService) {
        this.dongSanPhamService = dongSanPhamService;
        this.thuongHieuService = thuongHieuService;
        this.anhSanPhamService = anhSanPhamService;
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model,
                          @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        model.addAttribute("listSanPham", sanPhamService.getPage(pageNo, 5));

        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/view-add-san-pham")
    public String getViewAdd(@ModelAttribute("sanPham") SanPhamRequest request, Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("listDongSp", dongSanPhamService.getList());
        return "admin-template/san_pham/them_san_pham";
    }

    @PostMapping("/add")
    public String saveProduct(@Valid @ModelAttribute("sanPham") SanPhamRequest sanPham,
                              @RequestParam("fileImage") MultipartFile multipartFile,
                              BindingResult bindingResult, Model model) {
//        System.out.println(multipartFile.getOriginalFilename());

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            AnhSanPham anhSanPham = new AnhSanPham();
            SanPham savedSanPham = sanPhamService.save(sanPham);
            anhSanPham.setSanPham(savedSanPham);
            anhSanPham.setUrl(multipartFile.getOriginalFilename());
            anhSanPhamService.save(anhSanPham);

            String uploadDir = "src/main/resources/static/images/";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            return "admin-template/san_pham/them_san_pham";
        }
        return "redirect:/admin/san-pham/hien-thi";

    }
}

