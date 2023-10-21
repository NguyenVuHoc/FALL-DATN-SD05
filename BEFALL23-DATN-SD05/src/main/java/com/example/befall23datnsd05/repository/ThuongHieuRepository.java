package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {


    @Query(value = "select p from ThuongHieu p", nativeQuery = false)
    Page<ThuongHieuCustom> getPageThuongHieuCusTom(Pageable pageable);

    @Query(value = "select p from ThuongHieu p", nativeQuery = false)
    Page<ThuongHieuCustom> getPageThuongHieuCusTomTest(String keywork, Pageable pageable);

    @Query(value = "select thuong_hieu.id,thuong_hieu.ma,thuong_hieu.ten,thuong_hieu.trang_thai from thuong_hieu  where thuong_hieu.trang_thai='0'", nativeQuery = true)
    Page<ThuongHieuCustom> getThuongHieuByTrangThaiHoatDong(Pageable pageable);

    @Query(value = "select thuong_hieu.id,thuong_hieu.ma,thuong_hieu.ten,thuong_hieu.trang_thai from thuong_hieu  where thuong_hieu.trang_thai='1'", nativeQuery = true)
    Page<ThuongHieuCustom> getThuongHieuByTrangThaiDungHoatDong(Pageable pageable);

    boolean existsByMa(String ma);

}
