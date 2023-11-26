package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.UploadFile.FileUploadUtil;
import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
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
import java.util.ArrayList;
import java.util.Arrays;
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

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));

    @GetMapping()
    public String hienThi(Model model) {
        model.addAttribute("listSanPham", sanPhamService.getAll());
        model.addAttribute("index", pageNo + 1);
        model.addAttribute("trangThais", list);
        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        model.addAttribute("trangThais", list);
        model.addAttribute("listSanPham", sanPhamService.getByTrangThai(trangThai));
        return "admin-template/san_pham/san_pham";
    }


    @GetMapping("/view-add-san-pham")
    public String getViewAdd(Model model) {
        model.addAttribute("sanPham", new SanPhamRequest());
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("listDongSp", dongSanPhamService.getList());
        return "admin-template/san_pham/them_san_pham";
    }


    @PostMapping("/add")
    public String saveProduct(@Valid @ModelAttribute("sanPham") SanPhamRequest sanPham,
                              BindingResult result,
                              RedirectAttributes ra,
                              @RequestParam("fileImage") MultipartFile[] multipartFiles,
                              Model model) {
        String ma = sanPham.getMa();
        String ten = sanPham.getTen();
        if (result.hasErrors()) {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            return "admin-template/san_pham/them_san_pham";
        }
        if (multipartFiles.length > 4) {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            model.addAttribute("errorAnh", "Chỉ được tải lên tối đa 4 ảnh");
            return "admin-template/san_pham/them_san_pham";
        }
        for (MultipartFile multipartFile : multipartFiles) {
            if (multipartFile.getSize() >= 1048576) {
                model.addAttribute("listThuongHieu", thuongHieuService.getList());
                model.addAttribute("listDongSp", dongSanPhamService.getList());
                model.addAttribute("errorAnh", "Ảnh phải có kích cỡ nhỏ hơn 2KB");
                return "admin-template/san_pham/them_san_pham";
            }
        }

        if (sanPhamService.existByMa(ma)) {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            model.addAttribute("errorMa", "Mã đã tồn tại");
            return "admin-template/san_pham/them_san_pham";
        }

        if (sanPhamService.existsByTen(ten)) {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            model.addAttribute("errorTen", "Tên đã tồn tại");
            return "admin-template/san_pham/them_san_pham";
        }

        if (multipartFiles.length > 0) {
            // Lấy ảnh đầu tiên từ danh sách
            MultipartFile firstImage = multipartFiles[0];
            String fileName = StringUtils.cleanPath(firstImage.getOriginalFilename());
            sanPham.setAnhChinh(fileName);

            // Xử lý lưu sản phẩm
            SanPham savedSanPham = sanPhamService.save(sanPham);

            for (MultipartFile multipartFile : multipartFiles) {
                fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                AnhSanPham anhSanPham = new AnhSanPham();
                anhSanPham.setSanPham(savedSanPham);
                anhSanPham.setUrl(fileName);
                anhSanPhamService.save(anhSanPham);

                String uploadDir = "src/main/resources/static/images/";
                FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            }
        } else {
            ra.addFlashAttribute("chuadoianh", "Hãy chọn ít nhất một ảnh");
            return "redirect:/admin/san-pham/view-add-san-pham";
        }

        // ...
        return "redirect:/admin/san-pham?success";
    }


    @GetMapping("edit/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {

        SanPham sanPham = sanPhamService.findById(id);
        List<AnhSanPham> listAnh = sanPham.getListAnhSanPham();
        model.addAttribute("listThuongHieu", thuongHieuService.getList());
        model.addAttribute("listDongSp", dongSanPhamService.getList());
        model.addAttribute("listAnh", listAnh);
        model.addAttribute("sanPham", sanPham);
        return "admin-template/san_pham/sua_san_pham";
    }

    @PostMapping("/update")
    public String updateProduct(@Valid @ModelAttribute("sanPham") SanPhamRequest sanPhamRequest,
                                BindingResult result, Model model) {
        String ten = sanPhamRequest.getTen();
        Long id = sanPhamRequest.getId();
        SanPham existingSanPham = sanPhamService.findById(id);
        List<AnhSanPham> existingImg = existingSanPham.getListAnhSanPham(); // Danh sách ảnh mới
        List<String> newImageUrls = new ArrayList<>();
        if(result.hasErrors()){
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            return "admin-template/san_pham/sua_san_pham";
        }
        if (sanPhamService.existsByTenAndIdNot(ten, id)) {
            model.addAttribute("listThuongHieu", thuongHieuService.getList());
            model.addAttribute("listDongSp", dongSanPhamService.getList());
            model.addAttribute("errorTen", "Tên đã tồn tại");
            return "redirect:/admin/san-pham/edit/" + id;
        }

        List<MultipartFile> multipartFiles = sanPhamRequest.getFileImages();
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<AnhSanPham> newImages = new ArrayList<>();
            String uploadDir = "src/main/resources/static/images";
            anhSanPhamService.deleteByIdSp(id);

            for (MultipartFile multipartFile : multipartFiles) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    AnhSanPham anhSanPham = new AnhSanPham();
                    anhSanPham.setSanPham(existingSanPham);
                    existingSanPham.setAnhChinh(fileName);
                    anhSanPham.setUrl(fileName);
                    anhSanPhamService.save(anhSanPham);
                    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                    newImages.add(anhSanPham);
                    newImageUrls.add(anhSanPham.getUrl());
                    String firstImageUrl = newImageUrls.get(0);
                    sanPhamRequest.setAnhChinh(firstImageUrl);
                }
            }
            existingImg.addAll(newImages);
        }

        existingSanPham.setListAnhSanPham(existingImg);
        sanPhamService.update(sanPhamRequest);
        return "redirect:/admin/san-pham?success";
    }
}

