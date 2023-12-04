package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    private MauSacService  mauSacService;
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


    @GetMapping("/cua-hang")
    public String getAllShopCustomer(Model model) {
        Page<ChiTietSanPham> pageAll = chiTietSanPhamService.pageAllInShop(pageNo, 25);
        model.addAttribute("listCTSP", pageAll.stream().toList());
        model.addAttribute("index", pageNo + 1);
        List<ChiTietSanPhamCustomerCustom> list3custom = chiTietSanPhamService.list3Custom();
        model.addAttribute("list3Custom", list3custom.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3limited = chiTietSanPhamService.list3Limited();
        model.addAttribute("list3Limited", list3limited.stream().toList());

//        truyền vào filter
        model.addAttribute("pageNo",pageNo);
        model.addAttribute("dongSps",dongSanPhamService.getList());
        model.addAttribute("thuongHieus",thuongHieuService.getList());
        model.addAttribute("deGiays",deGiayService.getAll());
        model.addAttribute("mauSacs",mauSacService.getAll());
        model.addAttribute("coGiays",coGiayService.getAll());
        model.addAttribute("lotGiays",lotGiayService.getAll());
        model.addAttribute("kichThuocs",kichThuocService.getAll());
        return "customer-template/shop";
    }

    @GetMapping("/cua-hang-pre")
    private String preCustome() {
        pageNo--;
        pageNo = chiTietSanPhamService.nextPage(pageNo);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/cua-hang-first")
    private String firstCustome() {
        pageNo = chiTietSanPhamService.nextPage(0);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/cua-hang-last")
    private String lastCustome() {
        Integer sizeList = chiTietSanPhamRepository.findAll().size();
        Integer pageCount = (int) Math.ceil((double) sizeList / 20);
        pageNo = pageCount -1;
        pageNo = chiTietSanPhamService.nextPage(pageNo);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/cua-hang-next")
    private String nextCustomer() {
        pageNo++;
        pageNo = chiTietSanPhamService.nextPage(pageNo);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/trang-chu")
    public String get3TrangChuCustomer(Model model){
        List<ChiTietSanPhamCustomerCustom> list3new = chiTietSanPhamService.list3New();
        model.addAttribute("list3New", list3new.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3prominent = chiTietSanPhamService.list3Prominent();
        model.addAttribute("list3Prominent", list3prominent.stream().toList());
        return "customer-template/index";
    }

    @GetMapping("/chi-tiet-san-pham/{id}")
    public String detailCustomerSanPham(@PathVariable("id") Long id, Model model){
        ChiTietSanPham chiTietSanPham = chiTietSanPhamService.getById(id);
        model.addAttribute("spDetail", chiTietSanPham);
        List<AnhCustomerCustom> listAnhdetail = chiTietSanPhamService.listAnhDetail(id);
        model.addAttribute("listAnhDetail", listAnhdetail.stream().toList());
        List<ChiTietSanPhamCustomerCustom> listRand = chiTietSanPhamService.list4Random();
        model.addAttribute("listRandom", listRand.stream().toList());
        List<ChiTietSanPhamCustomerCustom> listRand2 = chiTietSanPhamService.list4Random();
        model.addAttribute("listRandom2", listRand2.stream().toList());
        model.addAttribute("soLuongTon", chiTietSanPham.getSoLuongTon());
        List<GioHangChiTiet> cartItems = gioHangChiTietService.getAll(Long.valueOf(5));

        // Tìm mục trong giỏ hàng dựa trên ID sản phẩm
        GioHangChiTiet gioHangChiTiet = cartItems.stream()
                .filter(item -> item.getChiTietSanPham().getId().equals(id))
                .findFirst()
                .orElse(null);

        int soLuongTrongGioHang = (gioHangChiTiet != null) ? gioHangChiTiet.getSoLuong() : 0;
        model.addAttribute("soLuongTrongGioHang", soLuongTrongGioHang);
        return "customer-template/detail";
    }
}
