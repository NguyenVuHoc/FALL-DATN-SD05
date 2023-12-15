package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.dto.HDCT.ChiTietSanPhamDTO;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.service.*;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/wingman")
public class ChiTietSanPhamCustomerController {

    @Autowired
    private ChiTietSanPhamCustomerService chiTietSanPhamService;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private GioHangChiTietService gioHangChiTietService;

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
    private DongSanPhamService dongSanPhamService;
    @Autowired
    private ThuongHieuService thuongHieuService;
    Integer pageNo = 0;
    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    @GetMapping("/cua-hang")
    public String getAllShopCustomer(Model model, @RequestParam(name = "tenThuongHieu", defaultValue = "", required = false) List<String> tenThuongHieu,
                                     @RequestParam(name = "tenDongSanPham", defaultValue = "", required = false) List<String> tenDongSanPham,
                                     @RequestParam(name = "tenKichThuoc", defaultValue = "", required = false) List<String> tenKichThuoc,
                                     @RequestParam(name = "tenLotGiay", defaultValue = "", required = false) List<String> tenLotGiay,
                                     @RequestParam(name = "tenCoGiay", defaultValue = "", required = false) List<String> tenCoGiay,
                                     @RequestParam(name = "tenDeGiay", defaultValue = "", required = false) List<String> tenDeGiay,
                                     @RequestParam(name = "tenMauSac", defaultValue = "", required = false) List<String> tenMauSac,
                                     @RequestParam(name = "minGia", defaultValue = "0", required = false) Double minGia,
                                     @RequestParam(name = "maxGia", defaultValue = "", required = false) Double maxGia,
                                     @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                     @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
                                     @RequestParam(name = "tenSanPham", defaultValue = "") String tenSanPham,
                                     @RequestParam(name = "sortField", defaultValue = "0", required = false) String sortField) {
        Long id = principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        if (maxGia == null) {
            maxGia = 1000000000000.0;
        }
        model.addAttribute("checkSecurity",checkSecurity);

        Page<ChiTietSanPhamDTO> chiTietSanPhamPage = chiTietSanPhamService.findAllByCondition(tenThuongHieu, tenDongSanPham, tenKichThuoc, tenLotGiay, tenCoGiay, tenDeGiay, tenMauSac, minGia, maxGia, page, pageSize, sortField, tenSanPham);
        int totalPages = chiTietSanPhamPage.getTotalPages();
        long totalCount = chiTietSanPhamPage.getTotalElements();

        if (minGia > maxGia) {
            model.addAttribute("listThuongHieu", tenThuongHieu);
            model.addAttribute("listDongSanPham", tenDongSanPham);
            model.addAttribute("listLotGiay", tenLotGiay);
            model.addAttribute("listCoGiay", tenCoGiay);
            model.addAttribute("listDeGiay", tenDeGiay);
            model.addAttribute("listMauSac", tenMauSac);
            model.addAttribute("tenSanPham", tenSanPham);
            model.addAttribute("listKichThuoc", tenKichThuoc);
            model.addAttribute("minGia", minGia);
            model.addAttribute("maxGia", maxGia);
            model.addAttribute("sortField", sortField);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("dongSps", dongSanPhamService.getList());
            model.addAttribute("thuongHieus", thuongHieuService.getList());
            model.addAttribute("deGiays", deGiayService.getAll());
            model.addAttribute("mauSacs", mauSacService.getAll());
            model.addAttribute("coGiays", coGiayService.getAll());
            model.addAttribute("lotGiays", lotGiayService.getAll());
            model.addAttribute("kichThuocs", kichThuocService.getAll());
            model.addAttribute("err", "mời bạn nhập đúng khoảng giá!");
            model.addAttribute("index", pageNo + 1);
            model.addAttribute("listCTSP", chiTietSanPhamService.findAllByCondition(null, null, null, null, null, null, null, null, null, page, pageSize, sortField, tenSanPham).stream().toList());
            List<ChiTietSanPhamCustomerCustom> list3custom = chiTietSanPhamService.list3Custom();
            model.addAttribute("list3Custom", list3custom.stream().toList());
            List<ChiTietSanPhamCustomerCustom> list3limited = chiTietSanPhamService.list3Limited();
            model.addAttribute("list3Limited", list3limited.stream().toList());
            model.addAttribute("page", page);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalCount", totalCount);
            return "customer-template/shop";
        }


        model.addAttribute("tenSanPham", tenSanPham);
        model.addAttribute("listThuongHieu", tenThuongHieu);
        model.addAttribute("listDongSanPham", tenDongSanPham);
        model.addAttribute("listLotGiay", tenLotGiay);
        model.addAttribute("listCoGiay", tenCoGiay);
        model.addAttribute("listDeGiay", tenDeGiay);
        model.addAttribute("listMauSac", tenMauSac);
        model.addAttribute("listKichThuoc", tenKichThuoc);
        model.addAttribute("minGia", minGia);
        model.addAttribute("maxGia", maxGia);
        model.addAttribute("sortField", sortField);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCount", totalCount);

        model.addAttribute("index", pageNo + 1);
        model.addAttribute("listCTSP", chiTietSanPhamPage.getContent());
        List<ChiTietSanPhamCustomerCustom> list3custom = chiTietSanPhamService.list3Custom();
        model.addAttribute("list3Custom", list3custom.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3limited = chiTietSanPhamService.list3Limited();
        model.addAttribute("list3Limited", list3limited.stream().toList());

//        truyền vào filter
        model.addAttribute("dongSps", dongSanPhamService.getList());
        model.addAttribute("thuongHieus", thuongHieuService.getList());
        model.addAttribute("deGiays", deGiayService.getAll());
        model.addAttribute("mauSacs", mauSacService.getAll());
        model.addAttribute("coGiays", coGiayService.getAll());
        model.addAttribute("lotGiays", lotGiayService.getAll());
        model.addAttribute("kichThuocs", kichThuocService.getAll());
        return "customer-template/shop";
    }

    @GetMapping("/reset")
    private String preCustome() {
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/trang-chu")
    public String get3TrangChuCustomer(Model model) {
        Long id=principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
        }
        model.addAttribute("checkSecurity",checkSecurity);
        List<ChiTietSanPhamCustomerCustom> list3new = chiTietSanPhamService.list3New();
        model.addAttribute("list3New", list3new.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3prominent = chiTietSanPhamService.list3Prominent();
        model.addAttribute("list3Prominent", list3prominent.stream().toList());
        return "customer-template/index";
    }

    @GetMapping("/chi-tiet-san-pham/{id}")
    public String detailCustomerSanPham(@PathVariable("id") Long id, Model model) {
        Long idKh=principalKhachHang.getCurrentUserId();
        Boolean checkSecurity=false;
        if (id != null) {
            checkSecurity= true;
            model.addAttribute("checkSecurity",checkSecurity);
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(id);
            model.addAttribute("spDetail", chiTietSanPham);
            List<AnhCustomerCustom> listAnhdetail = chiTietSanPhamService.listAnhDetail(id);
            model.addAttribute("listAnhDetail", listAnhdetail.stream().toList());
            List<ChiTietSanPhamCustomerCustom> listRand = chiTietSanPhamService.list4Random();
            model.addAttribute("listRandom", listRand.stream().toList());
            List<ChiTietSanPhamCustomerCustom> listRand2 = chiTietSanPhamService.list4Random();
            model.addAttribute("listRandom2", listRand2.stream().toList());
            model.addAttribute("soLuongTon", chiTietSanPham.getSoLuongTon());
        }else {
            model.addAttribute("checkSecurity",checkSecurity);
            ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(id);
            model.addAttribute("spDetail", chiTietSanPham);
            List<AnhCustomerCustom> listAnhdetail = chiTietSanPhamService.listAnhDetail(id);
            model.addAttribute("listAnhDetail", listAnhdetail.stream().toList());
            List<ChiTietSanPhamCustomerCustom> listRand = chiTietSanPhamService.list4Random();
            model.addAttribute("listRandom", listRand.stream().toList());
            List<ChiTietSanPhamCustomerCustom> listRand2 = chiTietSanPhamService.list4Random();
            model.addAttribute("listRandom2", listRand2.stream().toList());
            model.addAttribute("soLuongTon", chiTietSanPham.getSoLuongTon());
            List<GioHangChiTiet> cartItems = gioHangChiTietService.getAll(idKh);

            // Tìm mục trong giỏ hàng dựa trên ID sản phẩm
            GioHangChiTiet gioHangChiTiet = cartItems.stream()
                    .filter(item -> item.getChiTietSanPham().getId().equals(id))
                    .findFirst()
                    .orElse(null);

            int soLuongTrongGioHang = (gioHangChiTiet != null) ? gioHangChiTiet.getSoLuong() : 0;
            model.addAttribute("soLuongTrongGioHang", soLuongTrongGioHang);
        }

        return "customer-template/detail";
    }

}
