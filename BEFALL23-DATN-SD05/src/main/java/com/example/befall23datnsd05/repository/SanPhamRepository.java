package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    @Query(value = "select p from SanPham p", nativeQuery = false)
    Page<SanPhamCustom> getPageSanPhamCusTom(Pageable pageable);

    @Query(value = "select p from SanPham p where p.trangThai=0", nativeQuery = false)
    Page<SanPhamCustom> getSanPhamByTrangThaiHoatDong(Pageable pageable);


    @Query(value = "select p from SanPham p where p.trangThai=1", nativeQuery = false)
    Page<SanPhamCustom> getSanPhamByTrangThaiDungHoatDong(Pageable pageable);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);


}
