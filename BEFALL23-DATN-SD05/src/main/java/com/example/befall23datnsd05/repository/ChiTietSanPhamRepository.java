package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Long> {

    @Query(value = "select san_pham.ten, chi_tiet_san_pham.gia_ban, chi_tiet_san_pham.id, san_pham.anh_chinh \n" +
            "from chi_tiet_san_pham \n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3New();

    @Query(value = "select san_pham.ten, chi_tiet_san_pham.gia_ban, chi_tiet_san_pham.id, san_pham.anh_chinh \n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham  on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "where san_pham.id_thuong_hieu like 6 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Prominent();

    @Query(value = "select san_pham.ten, chi_tiet_san_pham.gia_ban, chi_tiet_san_pham.id, san_pham.anh_chinh \n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham  on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "where san_pham.id_dong_san_pham like 24 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Custom();

    @Query(value = "select san_pham.ten, chi_tiet_san_pham.gia_ban, chi_tiet_san_pham.id, san_pham.anh_chinh \n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham  on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "where san_pham.id_dong_san_pham like 23 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Limited();


}
