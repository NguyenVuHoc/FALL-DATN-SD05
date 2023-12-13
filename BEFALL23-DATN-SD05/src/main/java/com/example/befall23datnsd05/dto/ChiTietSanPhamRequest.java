package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.EnumType.ORDINAL;

@Setter
@Getter
public class ChiTietSanPhamRequest {

//    private String sanPham;

    private Long id;

    private Long deGiay;

    private Long mauSac;

    private Long kichThuoc;

    private Long lotGiay;

    private Long coGiay;

    private Long sanPham;

    @NotNull(message = "Số lượng tồn không được để trống")
    @Min(value = 0, message = "Số lượng tồn phải lớn hơn hoặc bằng 0")
    private Integer soLuongTon;

    @NotNull(message = "Giá mặc định không được để trống")
    @DecimalMin(value = "0.0", message = "Giá mặc định phải lớn hơn hoặc bằng 0")
    private BigDecimal giaMacDinh;

//    @NotNull(message = "Giá bán không được để trống")
//    @DecimalMin(value = "0.0", message = "Giá bán định phải lớn hơn hoặc bằng 0")
    private BigDecimal giaBan;

    private TrangThai trangThai;

}
