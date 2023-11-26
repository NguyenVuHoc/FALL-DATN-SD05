package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {

    GioHangChiTiet findByGioHangAndChiTietSanPhamAndHoaDonIsNull(GioHang gioHang, ChiTietSanPham chiTietSanPham);

//    List<GioHangChiTiet> findAllByByGioHangAndHoaDonIsNull(Long idGioHang);

    //    List<GioHangChiTiet> findAllByGioHangAndHoaDonIsNull();
    @Query(value = "select * from gio_hang_chi_tiet where id_hoa_don=?1", nativeQuery = true)
    List<GioHangChiTiet> findByHoaDon(long id);


    @Query(value = "select * from gio_hang_chi_tiet " +
            "where id_hoa_don is null and id_gio_hang = :idGioHang", nativeQuery = true)
    List<GioHangChiTiet> findAllByGioHang(@Param("idGioHang") Long idGioHang);

    @Query(value = "SELECT count(so_luong) from gio_hang_chi_tiet join hoa_don  where gio_hang_chi_tiet.trang_thai=6 \n" +
            "  AND hoa_don.ngay_thanh_toan BETWEEN ?1 AND ?2\n",nativeQuery = true)
    Long sanPhamHoanTra(LocalDate from, LocalDate to);


    @Query(value = "SELECT ct.chiTietSanPham.sanPham.ten, SUM(ct.soLuong) AS tong_so_luong " +
            "FROM GioHangChiTiet ct " +
            "JOIN ct.hoaDon hd " +
            "WHERE hd.trangThai = com.example.befall23datnsd05.enumeration.TrangThaiDonHang.HOAN_THANH " +
            "  AND hd.ngayThanhToan BETWEEN ?1 AND ?2 \n"+
            "GROUP BY ct.chiTietSanPham.sanPham.ten " +
            "ORDER BY tong_so_luong DESC " +  // Adding an order to get top quantities first
            "LIMIT 5")
    List<Object[]> countAllGhct(LocalDate from, LocalDate to);



}
