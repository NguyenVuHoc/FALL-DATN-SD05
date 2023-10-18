package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {
    @Query(value = "select p from ThuongHieu p", nativeQuery = false)
    List<ThuongHieuCustom> getThuongHieuCusTom();

    @Query(value = "select p from ThuongHieu p", nativeQuery = false)
    Page<ThuongHieuCustom> getPageThuongHieuCusTom(Pageable pageable);

    @Query(value = "select p from ThuongHieu p where p.trangThai='0'", nativeQuery = false)
    Page<ThuongHieu> getThuongHieuByTrangThaiHoatDong(TrangThai trangThai, Pageable pageable);


    @Query(value = "select p from ThuongHieu p where p.trangThai='1'", nativeQuery = false)
    Page<ThuongHieu> getThuongHieuByTrangThaiDungHoatDong(TrangThai trangThai, Pageable pageable);
}
