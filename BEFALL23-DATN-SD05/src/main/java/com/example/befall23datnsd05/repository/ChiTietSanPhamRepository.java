package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
            "where dong_san_pham.ten = 'Giày custom' limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Custom();

    @Query(value = "select san_pham.ten as tenSp, san_pham.anh_chinh, chi_tiet_san_pham.id, chi_tiet_san_pham.gia_ban, de_giay.ten as tenDe_giay, co_giay.ten as tenCo_giay, lot_giay.ten as tenLot_giay, dong_san_pham.ten as tenDongSp\n" +
            "from chi_tiet_san_pham\n" +
            "join san_pham on chi_tiet_san_pham.id_san_pham = san_pham.id \n" +
            "join de_giay on chi_tiet_san_pham.id_de_giay = de_giay.id \n" +
            "join co_giay on chi_tiet_san_pham.id_co_giay = co_giay.id \n" +
            "join lot_giay on chi_tiet_san_pham.id_lot_giay = lot_giay.id \n" +
            "join dong_san_pham on san_pham.id_dong_san_pham = dong_san_pham.id \n" +
            "where dong_san_pham.ten = 'Giày limited' limit 3", nativeQuery = true)
    List<ChiTietSanPhamCustomerCustom> list3Limited();

    @Query(value = "select anh.url, chi_tiet_san_pham.id from chi_tiet_san_pham\n" +
            "join san_pham on san_pham.id = chi_tiet_san_pham.id_san_pham\n" +
            "join anh on anh.id_san_pham = san_pham.id\n" +
            "where chi_tiet_san_pham.id = :id", nativeQuery = true)
    List<AnhCustomerCustom> listAnhDetail(Long id);

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


    @Query(value = "select * from chi_tiet_san_pham where trang_thai = 0", nativeQuery = true)
    Page<ChiTietSanPham> getTrangThaiHoatDong(Pageable pageable);

    @Query(value = "select * from chi_tiet_san_pham where trang_thai = 1", nativeQuery = true)
    Page<ChiTietSanPham> getTrangThaiDungHoatDong(Pageable pageable);

    Page<ChiTietSanPham> findBySanPham_TenContainingIgnoreCase(String ten, Pageable pageable);
}
