package com.example.befall23datnsd05.entity;

import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.EnumType.ORDINAL;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "nhan_vien")
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "ma", unique = true)
    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @Column(name = "ten")
    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @Column(name = "sdt", unique = true)
    @NotBlank(message = "Sđt không được để trống")
    private String sdt;

    @Column(name = "gioi_tinh")
    @Enumerated(ORDINAL)
    private GioiTinh gioiTinh;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_sua")
    private LocalDate ngaySua;

    @Column(name = "email")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @Column(name = "mat_khau")
    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;

    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;



}