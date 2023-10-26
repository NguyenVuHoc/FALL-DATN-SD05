package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.example.befall23datnsd05.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Long> {

    @Query(value = "select * from chi_tiet_san_pham where id = :idChiTietSanPham", nativeQuery = true)
    Optional<ChiTietSanPham> getChiTietSanPhamById(@Param("idChiTietSanPham") Long idChiTietSanPham);

    @Query(value = "select chi_tiet_san_pham.id_san_pham, chi_tiet_san_pham.id_de_giay, chi_tiet_san_pham.id_mau_sac,\n" +
            " chi_tiet_san_pham.id_kich_thuoc, chi_tiet_san_pham.id_lot_giay,\n" +
            " chi_tiet_san_pham.id_co_giay, chi_tiet_san_pham.so_luong_ton,\n" +
            " chi_tiet_san_pham.gia_mac_dinh, chi_tiet_san_pham.gia_ban\n" +
            "from chi_tiet_san_pham", nativeQuery = true)
    List<ChiTietSanPhamCustom> getAll();

    @Query(value = "select chi_tiet_san_pham.id_san_pham, chi_tiet_san_pham.id_de_giay, chi_tiet_san_pham.id_mau_sac,\n" +
            " chi_tiet_san_pham.id_kich_thuoc, chi_tiet_san_pham.id_lot_giay,\n" +
            " chi_tiet_san_pham.id_co_giay, chi_tiet_san_pham.so_luong_ton,\n" +
            " chi_tiet_san_pham.gia_mac_dinh, chi_tiet_san_pham.gia_ban\n" +
            "from chi_tiet_san_pham", nativeQuery = true)
    Page<ChiTietSanPhamCustom> getPage(PageRequest page);
}
