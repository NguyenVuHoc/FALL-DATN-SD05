package com.example.befall23datnsd05.entity;

import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

import static jakarta.persistence.EnumType.ORDINAL;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "san_pham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ma", nullable = false, unique = true)
    private String ma;

    @Column(name = "ten")
    private String ten;

    @Column(name = "mo_ta")
    private String moTa;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_sua")
    private LocalDate ngaySua;

    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL)
    private List<AnhSanPham> listAnhSanPham;
}
