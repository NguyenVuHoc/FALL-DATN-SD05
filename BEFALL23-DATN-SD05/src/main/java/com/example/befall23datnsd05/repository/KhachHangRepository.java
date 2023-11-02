package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    Optional<KhachHang> findByEmail(String email);

    Page<KhachHang> findByTenContains(String ten, Pageable pageable);

    boolean existsByMa(String ma);

    @Query(value = "select * from khach_hang where trang_thai = 0", nativeQuery = true)
    Page<KhachHang> getTrangThaiHoatDong(Pageable pageable);

    @Query(value = "select * from khach_hang where trang_thai = 1", nativeQuery = true)
    Page<KhachHang> getTrangThaiDungHoatDong(Pageable pageable);
}
