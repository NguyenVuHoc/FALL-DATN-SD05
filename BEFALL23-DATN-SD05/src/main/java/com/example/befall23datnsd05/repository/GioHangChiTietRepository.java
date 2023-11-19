package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {

    GioHangChiTiet findByGioHangAndChiTietSanPhamAndHoaDonIsNull(GioHang gioHang, ChiTietSanPham chiTietSanPham);

//    List<GioHangChiTiet> findAllByByGioHangAndHoaDonIsNull(Long idGioHang);

//    List<GioHangChiTiet> findAllByGioHangAndHoaDonIsNull();

    @Query(value = "select * from gio_hang_chi_tiet " +
            "where id_hoa_don is null and id_gio_hang = :idGioHang",nativeQuery = true)
    List<GioHangChiTiet> findAllByGioHang(@Param("idGioHang")Long idGioHang);


}
