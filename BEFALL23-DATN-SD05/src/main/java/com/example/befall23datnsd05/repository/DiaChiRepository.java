package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Long> {

    @Query(value ="select * from dia_chi where id_khach_hang= :idKhachHang",nativeQuery = true )
    List<DiaChi> getAllTheoKhachHang(@Param("idKhachHang") Long id);
}
