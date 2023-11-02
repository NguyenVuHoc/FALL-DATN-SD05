package com.example.befall23datnsd05.request;

import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DongSanPhamRequest {
    private Long id;
    @NotBlank(message = "Mã không được để trống!")
    private String ma;
    @NotBlank(message = "Tên không được để trống!")
    private String ten;

    private LocalDate ngayTao;

    private LocalDate ngaySua;

    private TrangThai trangThai;

}