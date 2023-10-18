package com.example.befall23datnsd05.request;

import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SanPhamRequest {
    private Long id;
    @NotBlank(message = "Mã không được để trống!")
    private String ma;
    @NotBlank(message = "Tên không được để trống!")
    private String ten;

    private Long idDongSanPham;

    private Long idThuongHieu;

    private List<AnhSanPham> listAnh = new ArrayList<>();

    private TrangThai trangThai;

    @NotBlank(message = "Mô tả không được để trống!")
    private String moTa;

}