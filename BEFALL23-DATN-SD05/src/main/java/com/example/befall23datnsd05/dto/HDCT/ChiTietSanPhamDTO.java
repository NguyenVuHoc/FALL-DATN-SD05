package com.example.befall23datnsd05.dto.HDCT;

import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.enumeration.TrangThai;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ChiTietSanPhamDTO {
    private Long id;
    private String tenSanPham;
    private String tenThuongHieu;
    private String tenDongSanPham;
    private String tenDeGiay;
    private String tenMauSac;
    private String tenKichThuoc;
    private String tenLotGiay;
    private String tenCoGiay;
    private Integer soLuongTon;
    private BigDecimal giaMacDinh;
    private BigDecimal giaBan;
    private KhuyenMai khuyenMai;
    private TrangThai tenTrangThai;
    private LocalDate ngayTao;
}
