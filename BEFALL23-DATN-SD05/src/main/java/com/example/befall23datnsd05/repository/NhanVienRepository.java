package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
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

    @Query("""
                SELECT nv FROM NhanVien nv
                WHERE 
                   nv.trangThai = :trangThai
            """)
    List<NhanVien> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai
    );

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);}
