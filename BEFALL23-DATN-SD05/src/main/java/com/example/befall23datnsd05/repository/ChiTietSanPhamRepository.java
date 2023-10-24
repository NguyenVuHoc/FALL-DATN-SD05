package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<ChiTietSanPham,Long> {

    @Query(value = "select * from chi_tiet_san_pham where id = :idChiTietSanPham", nativeQuery = true)
    Optional<ChiTietSanPham> getChiTietSanPhamById(@Param("idChiTietSanPham") Long idChiTietSanPham);


}
