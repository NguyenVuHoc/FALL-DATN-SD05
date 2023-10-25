package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham, Long> {

    @Query(value = "select san_pham.ten as tenSp, san_pham.anh_chinh, chi_tiet_san_pham.id, chi_tiet_san_pham.gia_ban, de_giay.ten as tenDe_giay, co_giay.ten as tenCo_giay, lot_giay.ten as tenLot_giay, dong_san_pham.ten as tenDongSp\n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "join de_giay on chi_tiet_san_pham.id_de_giay = de_giay.id \n" +
            "join co_giay on chi_tiet_san_pham.id_co_giay = co_giay.id \n" +
            "join lot_giay on chi_tiet_san_pham.id_lot_giay = lot_giay.id \n" +
            "join dong_san_pham on san_pham.id_dong_san_pham = dong_san_pham.id \n" +
            "limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3New();

    @Query(value = "select san_pham.ten as tenSp, san_pham.anh_chinh, chi_tiet_san_pham.id, chi_tiet_san_pham.gia_ban, de_giay.ten as tenDe_giay, co_giay.ten as tenCo_giay, lot_giay.ten as tenLot_giay, dong_san_pham.ten as tenDongSp\n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "join de_giay on chi_tiet_san_pham.id_de_giay = de_giay.id \n" +
            "join co_giay on chi_tiet_san_pham.id_co_giay = co_giay.id \n" +
            "join lot_giay on chi_tiet_san_pham.id_lot_giay = lot_giay.id \n" +
            "join dong_san_pham on san_pham.id_dong_san_pham = dong_san_pham.id \n" +
            "where san_pham.id_thuong_hieu like 6 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Prominent();

    @Query(value = "select san_pham.ten as tenSp, san_pham.anh_chinh, chi_tiet_san_pham.id, chi_tiet_san_pham.gia_ban, de_giay.ten as tenDe_giay, co_giay.ten as tenCo_giay, lot_giay.ten as tenLot_giay, dong_san_pham.ten as tenDongSp\n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "join de_giay on chi_tiet_san_pham.id_de_giay = de_giay.id \n" +
            "join co_giay on chi_tiet_san_pham.id_co_giay = co_giay.id \n" +
            "join lot_giay on chi_tiet_san_pham.id_lot_giay = lot_giay.id \n" +
            "join dong_san_pham on san_pham.id_dong_san_pham = dong_san_pham.id \n" +
            "where san_pham.id_dong_san_pham like 24 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Custom();

    @Query(value = "select san_pham.ten as tenSp, san_pham.anh_chinh, chi_tiet_san_pham.id, chi_tiet_san_pham.gia_ban, de_giay.ten as tenDe_giay, co_giay.ten as tenCo_giay, lot_giay.ten as tenLot_giay, dong_san_pham.ten as tenDongSp\n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "join de_giay on chi_tiet_san_pham.id_de_giay = de_giay.id \n" +
            "join co_giay on chi_tiet_san_pham.id_co_giay = co_giay.id \n" +
            "join lot_giay on chi_tiet_san_pham.id_lot_giay = lot_giay.id \n" +
            "join dong_san_pham on san_pham.id_dong_san_pham = dong_san_pham.id \n" +
            "where san_pham.id_dong_san_pham like 23 limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Limited();

    @Query(value = "select anh.url, chi_tiet_san_pham.id from chi_tiet_san_pham\n" +
            "join san_pham on san_pham.id = chi_tiet_san_pham.id_san_pham\n" +
            "join anh on anh.id_san_pham = san_pham.id\n" +
            "where chi_tiet_san_pham.id = :id", nativeQuery = true)
    List<AnhCustomerCustom> listAnhDetail(Long id);
}
