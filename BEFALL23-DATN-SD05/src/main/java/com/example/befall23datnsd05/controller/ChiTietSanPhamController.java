package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.service.ChiTietSanPhamCustomerService;
import com.example.befall23datnsd05.service.SanPhamCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChiTietSanPhamController {

    @Autowired
    private ChiTietSanPhamCustomerService chiTietSanPhamService;

    @Autowired
    private SanPhamCustomerService SanPhamService;

    Integer pageNo = 0;

    @GetMapping("/wingman/cua-hang")
    public String getAllShopCustomer(Model model) {
        Page<ChiTietSanPham> pageAll = chiTietSanPhamService.pageAllInShop(pageNo, 20);
        model.addAttribute("listCTSP", pageAll.stream().toList());
        model.addAttribute("index", pageNo + 1);
        List<ChiTietSanPhamCustomerCustom> list3custom = chiTietSanPhamService.list3Custom();
        model.addAttribute("list3Custom", list3custom.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3limited = chiTietSanPhamService.list3Limited();
        model.addAttribute("list3Limited", list3limited.stream().toList());
        return "customer-template/shop";
    }

    @GetMapping("/wingman/cua-hang-pre")
    private String preCustome() {
        pageNo--;
        pageNo = chiTietSanPhamService.nextPage(pageNo);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/wingman/cua-hang-next")
    private String nextCustomer() {
        pageNo++;
        pageNo = chiTietSanPhamService.nextPage(pageNo);
        return "redirect:/wingman/cua-hang";
    }

    @GetMapping("/wingman/trang-chu")
    public String get3TrangChuCustomer(Model model){
        List<ChiTietSanPhamCustomerCustom> list3new = chiTietSanPhamService.list3New();
        model.addAttribute("list3New", list3new.stream().toList());
        List<ChiTietSanPhamCustomerCustom> list3prominent = chiTietSanPhamService.list3Prominent();
        model.addAttribute("list3Prominent", list3prominent.stream().toList());
        return "customer-template/index";
    }

}
