package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.dto.hoadonchitiet.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HoaDonChiTietRepo extends JpaRepository<HoaDonChiTiet,Long> {
    @Query(value = "select * from hoa_don_chi_tiet where trang_thai = 3 and id_hoa_don = :idHoaDon", nativeQuery = true)
    List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(@Param("idHoaDon") Long idHoaDon);

//    @Query(value = "UPDATE set ")
//    List<HoaDonChiTiet> getHoaDonChiTietByaIdHoaDon(@Param("idHoaDon") Long idHoaDon);

}
