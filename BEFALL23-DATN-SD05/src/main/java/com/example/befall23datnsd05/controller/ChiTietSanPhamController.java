package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.ChiTietSanPhamRequest;
import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.importFile.FileExcelCTSP;
import com.example.befall23datnsd05.repository.*;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import com.example.befall23datnsd05.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    SanPhamRepository sanPhamRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    KichThuocRepository kichThuocRepository;

    @Autowired
    DeGiayRepository deGiayRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    LotGiayRepository lotGiayRepository;

    @Autowired
    CoGiayRepository coGiayRepository;

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    FileExcelCTSP importFileExcelCTSP;

    Integer pageNo = 0;

    List<TrangThai> list = new ArrayList<>(Arrays.asList(TrangThai.DANG_HOAT_DONG, TrangThai.DUNG_HOAT_DONG));

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping("/admin/chi-tiet-san-pham")
    public String getAll(
            Model model
    ){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("listCTSP",service.getAll());
        model.addAttribute("listDG",deGiayService.getAll());
        model.addAttribute("listMS",mauSacService.getAll());
        model.addAttribute("listKT",kichThuocService.getAll());
        model.addAttribute("listLG",lotGiayService.getAll());
        model.addAttribute("listCG",coGiayService.getAll());
        model.addAttribute("listSP",sanPhamService.getList());
        model.addAttribute("trangThais", list);
        model.addAttribute("index", pageNo+1);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/chi_tiet_san_pham/chi_tiet_san_pham";
    }

    @GetMapping("/admin/chi-tiet-san-pham/trang-thai/{trangThai}")
    public String getByTrangThai(Model model,
                                 @PathVariable("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("trangThais", list);
        model.addAttribute("listCTSP", service.getByTrangThai(trangThai));
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/chi_tiet_san_pham/chi_tiet_san_pham";
    }

    @GetMapping("/admin/chi-tiet-san-pham/view-add")
    public String viewAdd(
            @ModelAttribute("chiTietSanPham")ChiTietSanPham chiTietSanPham,
            Model model
    ){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("listDG",deGiayService.getAll().stream().sorted(Comparator.comparing(DeGiay::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("listMS",mauSacService.getAll().stream().sorted(Comparator.comparing(MauSac::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("listKT",kichThuocService.getAll().stream().sorted(Comparator.comparing(KichThuoc::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("listLG",lotGiayService.getAll().stream().sorted(Comparator.comparing(LotGiay::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("listCG",coGiayService.getAll().stream().sorted(Comparator.comparing(CoGiay::getId).reversed()).collect(Collectors.toList()));
        model.addAttribute("listSP",sanPhamService.getList());

        model.addAttribute("chiTietSanPham", new ChiTietSanPham());
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/chi_tiet_san_pham/them_chi_tiet_san_pham";
    }

    @PostMapping("/admin/chi-tiet-san-pham/add")
    public String add(
            @Valid
            @ModelAttribute("chiTietSanPham") ChiTietSanPhamRequest chiTietSanPham,
            BindingResult bindingResult,
            Model model
    ){
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        if(bindingResult.hasErrors()){
            model.addAttribute("listDG",deGiayService.getAll().stream().sorted(Comparator.comparing(DeGiay::getId).reversed()).collect(Collectors.toList()));
            model.addAttribute("listMS",mauSacService.getAll().stream().sorted(Comparator.comparing(MauSac::getId).reversed()).collect(Collectors.toList()));
            model.addAttribute("listKT",kichThuocService.getAll().stream().sorted(Comparator.comparing(KichThuoc::getId).reversed()).collect(Collectors.toList()));
            model.addAttribute("listLG",lotGiayService.getAll().stream().sorted(Comparator.comparing(LotGiay::getId).reversed()).collect(Collectors.toList()));
            model.addAttribute("listCG",coGiayService.getAll().stream().sorted(Comparator.comparing(CoGiay::getId).reversed()).collect(Collectors.toList()));
            model.addAttribute("listSP",sanPhamService.getList());
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
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
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("listDG",deGiayService.getAll());
        model.addAttribute("listMS",mauSacService.getAll());
        model.addAttribute("listKT",kichThuocService.getAll());
        model.addAttribute("listLG",lotGiayService.getAll());
        model.addAttribute("listCG",coGiayService.getAll());
        model.addAttribute("listSP",sanPhamService.getList());

        model.addAttribute("chiTietSanPham",service.getById(id));
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/chi_tiet_san_pham/sua_chi_tiet_san_pham";
    }

    @PostMapping("/admin/chi-tiet-san-pham/update")
    public String update(@Valid @ModelAttribute("chiTietSanPham") ChiTietSanPhamRequest chiTietSanPham,
                         BindingResult bindingResult,
                         Model model
    ) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("listDG",deGiayService.getAll());
            model.addAttribute("listMS",mauSacService.getAll());
            model.addAttribute("listKT",kichThuocService.getAll());
            model.addAttribute("listLG",lotGiayService.getAll());
            model.addAttribute("listCG",coGiayService.getAll());
            model.addAttribute("listSP",sanPhamService.getList());
            model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
            return "admin-template/chi_tiet_san_pham/sua_chi_tiet_san_pham";
        }
        service.update(chiTietSanPham);
        return "redirect:/admin/chi-tiet-san-pham?success";
    }

    @GetMapping("/admin/chi-tiet-san-pham/delete/{id}")
    public String delete(@PathVariable("id") Long id,Model model) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        try {
            service.remove(id);
            model.addAttribute("success", "Xóa thành công");
            return "redirect:/admin/chi-tiet-san-pham?success";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "Không thể xóa bản ghi vì có ràng buộc khóa ngoại.");
            return "redirect:/admin/chi-tiet-san-pham?errorMessage";
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa bản ghi.");
            return "redirect:/admin/chi-tiet-san-pham?errorMessage";
        }
    }
    
    @PostMapping("/admin/chi-tiet-san-pham/import-excel")
    public String importExcel(
            @RequestParam("file") MultipartFile file,
            RedirectAttributes attributes
    ) throws IOException {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        if (!file.isEmpty()) {
            try {
                // Sử dụng thư mục tạm thời của hệ thống
                String directory = System.getProperty("java.io.tmpdir");

                String fileName = file.getOriginalFilename();
                String filePath = directory + File.separator + fileName;
                FileExcelCTSP importFileExcelCTSP = new FileExcelCTSP();

                // Lưu file vào thư mục được chọn
                file.transferTo(new File(filePath));
                System.out.println(filePath);

                // Tiếp tục với quá trình nhập
                importFileExcelCTSP.ImportFile(filePath, sanPhamRepository, mauSacRepository,
                        kichThuocRepository, deGiayRepository,
                        chiTietSanPhamRepository, chiTietSanPhamService,
                        lotGiayRepository, coGiayRepository);

                if (importFileExcelCTSP.checkLoi() > 0) {
                    attributes.addFlashAttribute("thongBaoLoiImport", "Đã thêm sản phẩm thành công nhưng có một số sản phẩm lỗi, mời bạn kiểm tra lại trên file excel");
                    return "redirect:/admin/chi-tiet-san-pham";
                }
            } catch (Exception e) {
                attributes.addFlashAttribute("thongBaoLoiImport", "Sai định dạng file hoặc có lỗi xảy ra trong quá trình xử lý");
                return "redirect:/admin/chi-tiet-san-pham";
            }
            return "redirect:/admin/chi-tiet-san-pham?success";
        }
        attributes.addFlashAttribute("thongBaoLoiImport", "Bạn chưa chọn file excel nào");
        return "redirect:/admin/chi-tiet-san-pham";
    }

    @GetMapping("/admin/chi-tiet-san-pham/export-excel")
    public ResponseEntity<?> exportExcel(){
        return importFileExcelCTSP.createExcel();
    }

    @GetMapping("/admin/chi-tiet-san-pham/export-excel-mau")
    public ResponseEntity<?> exportExcelMau(){
        return importFileExcelCTSP.createExcelTemplate();
    }

    @PostMapping("/admin/chi-tiet-san-pham/add-de-giay")
    @ResponseBody
    public ResponseEntity<String> addDeGiay(@ModelAttribute("deGiay") DeGiay deGiay) {
        if (deGiayService.existByMa(deGiay.getMa())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã đế giày đã tồn tại");
        }

        if (deGiayService.existsByTen(deGiay.getTen())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên đế giày đã tồn tại");
        }
        deGiayService.add(deGiay);
        return ResponseEntity.ok("successfully");
    }

    @PostMapping("/admin/chi-tiet-san-pham/add-mau-sac")
    @ResponseBody
    public ResponseEntity<String> addMauSac(@ModelAttribute("mauSac") MauSac mauSac) {
        if (mauSacService.existByMa(mauSac.getMa())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã màu sắc đã tồn tại");
        }

        if (mauSacService.existsByTen(mauSac.getTen())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên màu sắc đã tồn tại");
        }
        mauSacService.add(mauSac);
        return ResponseEntity.ok("successfully");
    }

    @PostMapping("/admin/chi-tiet-san-pham/add-kich-thuoc")
    @ResponseBody
    public ResponseEntity<String> addMauSac(@ModelAttribute("kichThuoc") KichThuoc kichThuoc) {
        if (kichThuocService.existByMa(kichThuoc.getMa())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã kích thước đã tồn tại");
        }

        if (kichThuocService.existsByTen(kichThuoc.getTen())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên kích thước đã tồn tại");
        }
        kichThuocService.add(kichThuoc);
        return ResponseEntity.ok("successfully");
    }

    @PostMapping("/admin/chi-tiet-san-pham/add-lot-giay")
    @ResponseBody
    public ResponseEntity<String> addLotGiay(@ModelAttribute("lotGiay") LotGiay lotGiay) {
        if (lotGiayService.existByMa(lotGiay.getMa())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã lót giày đã tồn tại");
        }

        if (lotGiayService.existsByTen(lotGiay.getTen())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên lót giày đã tồn tại");
        }
        lotGiayService.add(lotGiay);
        return ResponseEntity.ok("successfully");
    }

    @PostMapping("/admin/chi-tiet-san-pham/add-co-giay")
    @ResponseBody
    public ResponseEntity<String> addCoGiay(@ModelAttribute("coGiay") CoGiay coGiay) {
        if (coGiayService.existByMa(coGiay.getMa())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã cổ giày đã tồn tại");
        }

        if (coGiayService.existsByTen(coGiay.getTen())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tên cổ giày đã tồn tại");
        }

        coGiayService.add(coGiay);
        return ResponseEntity.ok("successfully");
    }

}
