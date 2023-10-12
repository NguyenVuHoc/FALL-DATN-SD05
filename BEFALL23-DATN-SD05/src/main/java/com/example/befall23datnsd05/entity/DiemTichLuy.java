package com.example.befall23datnsd05.entity;

import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import static jakarta.persistence.EnumType.ORDINAL;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "diem_tich_luy")
public class DiemTichLuy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "diem")
    private Integer diem;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_sua")
    private LocalDate ngaySua;

    @Column(name = "so_luong_hoa_don")
    private Integer soLuongHoaDon;

    @Column(name = "tong_chi_tieu", precision = 19, scale = 2)
    private BigDecimal tongChiTieu;

    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "id_khach_hang")
    private KhachHang khachHang;

}