package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.NhanVien;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {

    Optional<NhanVien> findByEmail(String email);

    Page<NhanVien> findByTenContains(String ten, Pageable pageable);

    boolean existsByMa(String ma);

    @Query(value = "select * from nhan_vien where trang_thai = 0", nativeQuery = true)
    Page<NhanVien> getTrangThaiHoatDong(Pageable pageable);

    @Query(value = "select * from nhan_vien where trang_thai = 1", nativeQuery = true)
    Page<NhanVien> getTrangThaiDungHoatDong(Pageable pageable);
}
