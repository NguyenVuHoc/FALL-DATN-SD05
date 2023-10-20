package com.example.befall23datnsd05.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ChiTietSanPhamRequest {

    private String sanPham;

    private String deGiay;

    private String mauSac;


    private String kichThuoc;


    private String lotGiay;


    private String coGiay;

    private Integer soLuongTon;

    private BigDecimal giaMacDinh;

    private BigDecimal giaBan;

}
