package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.DongSanphamCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongSanPhamRepository extends JpaRepository<DongSanPham, Long> {

    @Query(value = "select p from DongSanPham p", nativeQuery = false)
    List<DongSanphamCustom> getDongSanPhamCusTom();

    @Query(value = "select p from DongSanPham p", nativeQuery = false)
    Page<DongSanphamCustom> getPageDongSanPhamCusTom(Pageable pageable);

    @Query(value = "select p from DongSanPham p where p.trangThai='0'", nativeQuery = false)
    Page<DongSanPham> getDongSanPhamByTrangThaiHoatDong(TrangThai trangThai, Pageable pageable);


    @Query(value = "select p from DongSanPham p where p.trangThai='1'", nativeQuery = false)
    Page<DongSanPham> getDongSanPhamByTrangThaiDungHoatDong(TrangThai trangThai, Pageable pageable);
}
