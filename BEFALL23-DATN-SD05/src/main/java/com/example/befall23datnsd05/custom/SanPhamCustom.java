package com.example.befall23datnsd05.custom;

import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface SanPhamCustom {
    Long getId();

    String getMa();

    String getTen();

    String getMoTa();

    Integer getTrangThai();

    ThuongHieu getThuongHieu();

    DongSanPham getDongSanPham();

    List<AnhSanPham> listAnh();

    String getMainImagePath();

}
