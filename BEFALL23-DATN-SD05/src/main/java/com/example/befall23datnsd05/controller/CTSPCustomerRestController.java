package com.example.befall23datnsd05.controller;

import com.example.befall23datnsd05.dto.HDCT.ChiTietSanPhamDTO;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.service.ChiTietSanPhamCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/wingman")
public class CTSPCustomerRestController {

    @Autowired
    private ChiTietSanPhamCustomerService chiTietSanPhamService;

    @GetMapping("/findAll")
    public List<ChiTietSanPhamDTO> findAll(
            @RequestParam(name = "tenThuongHieu", defaultValue = "",required = false) List<String> tenThuongHieu,
            @RequestParam(name = "tenDongSanPham", defaultValue = "",required = false) List<String> tenDongSanPham,
            @RequestParam(name = "tenKichThuoc", defaultValue = "",required = false) List<String> tenKichThuoc,
            @RequestParam(name = "tenLotGiay", defaultValue = "",required = false) List<String> tenLotGiay,
            @RequestParam(name = "tenCoGiay", defaultValue = "",required = false) List<String> tenCoGiay,
            @RequestParam(name = "tenDeGiay", defaultValue = "",required = false) List<String> tenDeGiay,
            @RequestParam(name = "tenMauSac", defaultValue = "",required = false) List<String> tenMauSac,
            @RequestParam(name = "minGia", defaultValue = "0",required = false) Double minGia,
            @RequestParam(name = "maxGia", defaultValue = "",required = false) Double maxGia,
            @RequestParam(name = "page", defaultValue = "0",required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(name = "sortField", defaultValue = "0",required = false) String sortField
    ){
        maxGia = Double.MAX_VALUE;
        return chiTietSanPhamService.findAllByCondition(
                tenThuongHieu,
                tenDongSanPham,
                tenKichThuoc,
                tenLotGiay,
                tenCoGiay,
                tenDeGiay,
                tenMauSac,
                minGia,
                maxGia,
                page,
                pageSize,
                sortField
        ).getContent();
    }
}
