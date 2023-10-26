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
import java.util.ArrayList;
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
        model.addAttribute("index", pageNo + 1);
        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/get-trang-thai-hoat-dong")
    public String getAllByActive(Model model) {
        model.addAttribute("listSanPham", sanPhamService.getPageByActivity(pageNo, 5));
        model.addAttribute("index", pageNo + 1);
        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/get-trang-thai-dung-hoat-dong")
    public String getAllByInActive(Model model) {
        model.addAttribute("listSanPham", sanPhamService.getPageByInActivity(pageNo, 5));
        model.addAttribute("index", pageNo + 1);
        return "admin-template/san_pham/san_pham";
    }

    @GetMapping("/pre")
    public String pre() {
        pageNo--;
        pageNo = sanPhamService.tranferPage(pageNo);
        return "redirect:/admin/san-pham";
    }

    @GetMapping("/next")
    public String next() {
        pageNo++;
        pageNo = sanPhamService.tranferPage(pageNo);
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
                              @RequestParam("fileImage") MultipartFile[] multipartFiles,
                              Model model) {
        String ma = sanPham.getMa();
        String ten = sanPham.getTen();
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
    public String updateProduct(@ModelAttribute("sanPham") SanPhamRequest sanPham, RedirectAttributes ra) {
        String ten = sanPham.getTen();
        Long id = sanPham.getId();
        List<MultipartFile> multipartFiles = sanPham.getFileImages(); // Danh sách ảnh mới
        SanPham existingSanPham = sanPhamService.findById(id);
        if (sanPhamService.existsByTenAndIdNot(ten, id)) {
            ra.addFlashAttribute("listThuongHieu", thuongHieuService.getList());
            ra.addFlashAttribute("listDongSp", dongSanPhamService.getList());
            ra.addFlashAttribute("errorTen", "Tên đã tồn tại");
            return "redirect:/admin/san-pham/edit/" + id;
        }
        if (multipartFiles != null && !multipartFiles.isEmpty()) {
            List<AnhSanPham> newImages = new ArrayList<>();
            // Tạo danh sách chứa URL của ảnh mới
            List<String> newImageUrls = new ArrayList<>();
            for (MultipartFile multipartFile : multipartFiles) {
                if (!multipartFile.isEmpty()) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    // Xử lý và lưu trữ ảnh vào cơ sở dữ liệu
                    AnhSanPham anhSanPham = new AnhSanPham();
                    anhSanPham.setSanPham(existingSanPham);
                    anhSanPham.setUrl(fileName);
                    sanPham.setAnhChinh(fileName);
                    anhSanPhamService.save(anhSanPham);
                    // Lưu trữ tệp ảnh vào hệ thống tệp
                    String uploadDir = "src/main/resources/static/images/";
                    FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
                    newImages.add(anhSanPham);
                    newImageUrls.add(anhSanPham.getUrl());
                }
            }
            // Loại bỏ các ảnh cũ ra khỏi danh sách ảnh
//            List<AnhSanPham> oldImages = existingSanPham.getListAnhSanPham();
//            List<AnhSanPham> imagesToRemove = new ArrayList<>();
//            for (AnhSanPham oldImage : oldImages) {
//                if (!newImageUrls.contains(oldImage.getUrl())) {
//                    imagesToRemove.add(oldImage);
//                }
//            }
//            oldImages.removeAll(imagesToRemove);
//            // Cập nhật danh sách ảnh với ảnh mới và cũ
//            existingSanPham.getListAnhSanPham().addAll(newImages);
//            // Cập hật ảnh chính (anhChinh) với URL của ảnh đầu tiên trong danh sách ảnh mới
//            if (!newImages.isEmpty()) {
//                existingSanPham.setAnhChinh(newImages.get(0).getUrl());
//            }
            sanPhamService.update(sanPham);
        }

        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
        return "redirect:/admin/san-pham?success";
    }



//    @PostMapping("/update")
//    public String updateProduct(@ModelAttribute("sanPham") SanPhamRequest sanPham, RedirectAttributes ra,
//                                @RequestParam("fileImage") MultipartFile multipartFile, Model model) {
//        String ten = sanPham.getTen();
//        Long id = sanPham.getId();
//
//        // Kiểm tra xem người dùng đã chọn một tệp mới hay không
//        if (!multipartFile.isEmpty()) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            sanPham.setAnhChinh(fileName);
//
//            AnhSanPham anhSanPham = new AnhSanPham();
//            SanPham savedSanPham = sanPhamService.update(sanPham);
//            anhSanPham.setSanPham(savedSanPham);
//            anhSanPham.setUrl(fileName);
//            anhSanPhamService.save(anhSanPham);
//
//            String uploadDir = "src/main/resources/static/images/";
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//        } else {
//            // Người dùng không chọn tệp mới, giữ nguyên thông tin ảnh hiện có
//            // Lấy thông tin ảnh từ cơ sở dữ liệu hoặc từ bất kỳ nguồn nào bạn lưu trữ nó
//            SanPham existingSanPham = sanPhamService.findById(id); // Thay thế findById bằng phương thức thích hợp
//            if (existingSanPham != null) {
//                sanPham.setAnhChinh(existingSanPham.getAnhChinh());
//                sanPhamService.update(sanPham);
//
//            }
//        }
//
//        // Tiếp tục xử lý khác
//        if (sanPhamService.existsByTenAndIdNot(ten, id)) {
//            model.addAttribute("listThuongHieu", thuongHieuService.getList());
//            model.addAttribute("listDongSp", dongSanPhamService.getList());
//            model.addAttribute("errorTen", "Tên đã tồn tại");
//            return "admin-template/san_pham/sua_san_pham";
//        }
//
//        ra.addFlashAttribute("message", "Thay Đổi Thành Công.");
//        return "redirect:/admin/san-pham?success";
//    }
}

