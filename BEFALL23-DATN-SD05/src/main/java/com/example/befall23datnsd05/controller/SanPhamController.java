package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.UploadFile.FileUploadUtil;
import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.request.SanPhamRequest;
import com.example.befall23datnsd05.service.AnhSanPhamService;
import com.example.befall23datnsd05.service.DongSanPhamService;
import com.example.befall23datnsd05.service.SanPhamService;
import com.example.befall23datnsd05.service.ThuongHieuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/san-pham")
@RequiredArgsConstructor
public class SanPhamController {
    private final DongSanPhamService dongSanPhamService;
    private final ThuongHieuService thuongHieuService;
    private final AnhSanPhamService anhSanPhamService;
    private final SanPhamService sanPhamService;
    Integer pageNo = 0;

    @GetMapping()
    public String hienThi(Model model,
                          @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo) {

        model.addAttribute("listSanPham", sanPhamService.getPage(pageNo, 5));
        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/pre")
    public String pre() {
        pageNo--;
        pageNo = sanPhamService.transferPage(pageNo);
        return "redirect:/admin/san-pham";
    }

    @GetMapping("/next")
    public String next() {
        pageNo++;
        pageNo = sanPhamService.transferPage(pageNo);
        return "redirect:/admin/san-pham";
    }

    @GetMapping("/view-add-san-pham")
    public String getViewAdd(@ModelAttribute("sanPham") SanPhamRequest request, Model model) {
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("listDongSp", dongSanPhamService.getList());
        return "admin-template/san_pham/them_san_pham";
    }

    @PostMapping("/add")
    public String saveProduct(@Valid @ModelAttribute("sanPham") SanPhamRequest sanPham, RedirectAttributes ra,
                              @RequestParam("fileImage") MultipartFile multipartFile,
                              Model model) {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            sanPham.setAnhChinh(fileName);
            AnhSanPham anhSanPham = new AnhSanPham();
            SanPham savedSanPham = sanPhamService.save(sanPham);
            anhSanPham.setSanPham(savedSanPham);
            anhSanPham.setUrl(multipartFile.getOriginalFilename());
            anhSanPhamService.save(anhSanPham);

            String uploadDir = "src/main/resources/static/images/";
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            ra.addFlashAttribute("chuadoianh", "Hãy chọn ảnh");
            return "redirect:/admin/san-pham/view-add-san-pham";
        }
        return "redirect:/admin/san-pham";
    }

    @GetMapping("edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {

        SanPham sanPham = sanPhamService.findById(id);
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("listDongSp", dongSanPhamService.getList());
        model.addAttribute("sanPham", sanPham);
        return "admin-template/san_pham/sua_san_pham";
    }

    @PostMapping("/update")
    public String updateProduct(SanPhamRequest sanPham, RedirectAttributes ra,
                                @RequestParam("fileImage") MultipartFile multipartFile) {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            sanPham.setAnhChinh(fileName);
            AnhSanPham anhSanPham = new AnhSanPham();
            SanPham savedSanPham = sanPhamService.update(sanPham);
            anhSanPham.setSanPham(savedSanPham);
            anhSanPham.setUrl(multipartFile.getOriginalFilename());
            anhSanPhamService.save(anhSanPham);

            String uploadDir = "src/main/resources/static/images/";

            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            // Nếu không có ảnh mới, giữ nguyên tên ảnh hiện tại từ trường ẩn
            sanPham.setAnhChinh(sanPham.getCurrentMainImage());
            sanPhamService.update(sanPham);
        }

        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
        return "redirect:/admin/san-pham";
    }
}

