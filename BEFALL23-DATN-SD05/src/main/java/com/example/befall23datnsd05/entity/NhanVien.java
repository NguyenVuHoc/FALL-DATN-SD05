package com.example.befall23datnsd05.entity;

import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "sdt", unique = true)
    private String sdt;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "nagy_sua")
    private LocalDate nagySua;

    @Column(name = "email")
    private String email;

    @Column(name = "mat_khau")
    private String matKhau;

    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;



}