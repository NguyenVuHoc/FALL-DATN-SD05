package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.Befall23DatnSd05Application;
import com.example.befall23datnsd05.dto.HoaDonChiTietCustom;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, Long> {

    @Query(value = "select  ten_san_pham,mau_sac, kich_thuoc, gia_ban, so_luong\n" +
            "from hoa_don_chi_tiet \n" +
            "where id_hoa_don = :idHoaDon", nativeQuery = true)
    List<HoaDonChiTiet> getHoaDonChiTietByIdHoaDon(@Param("idHoaDon") Long idHoaDon);


}


