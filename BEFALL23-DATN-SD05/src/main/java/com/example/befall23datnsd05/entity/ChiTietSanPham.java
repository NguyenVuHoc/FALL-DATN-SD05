package com.example.befall23datnsd05.entity;

import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
@Table(name = "chi_tiet_san_pham")
public class ChiTietSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_san_pham", referencedColumnName = "id")
    @JsonIgnore
    private SanPham sanPham;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinColumn(name = "id_de_giay", referencedColumnName = "id")
    @JsonManagedReference
    private DeGiay deGiay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_mau_sac", referencedColumnName = "id")
    @JsonManagedReference
    private MauSac mauSac;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_kich_thuoc", referencedColumnName = "id")
    @JsonManagedReference
    private KichThuoc kichThuoc;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_lot_giay", referencedColumnName = "id")
    @JsonManagedReference
    private LotGiay lotGiay;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_co_giay", referencedColumnName = "id")
    @JsonManagedReference
    private CoGiay coGiay;

    @Column(name = "so_luong_ton")
    private Integer soLuongTon;

    @Column(name = "gia_mac_dinh", precision = 19, scale = 2)
    private BigDecimal giaMacDinh;

    @Column(name = "gia_ban", precision = 19, scale = 2)
    private BigDecimal giaBan;

    @Column(name = "ngay_tao")
    private LocalDate ngayTao;

    @Column(name = "ngay_sua")
    private LocalDate ngaySua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khuyen_mai", referencedColumnName = "id", nullable = true)
    private KhuyenMai khuyenMai;

    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;

    public BigDecimal tinhGiaSauGiamGia() {
        if (khuyenMai != null && khuyenMai.getTrangThai() == TrangThaiKhuyenMai.DANG_HOAT_DONG) {
            BigDecimal mucGiam = BigDecimal.valueOf(khuyenMai.getMucGiamGia() / 100.0);
            BigDecimal giaSauGiamGia = giaMacDinh.subtract(giaMacDinh.multiply(mucGiam));
            return giaSauGiamGia.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : giaSauGiamGia;
        } else
            return giaMacDinh;
    }

    public BigDecimal GiaSauGiamGia() {
        BigDecimal mucGiam = BigDecimal.valueOf(khuyenMai.getMucGiamGia() / 100.0);
        BigDecimal giaSauGiamGia = giaMacDinh.subtract(giaMacDinh.multiply(mucGiam));
        return giaSauGiamGia.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : giaSauGiamGia;
    }

}