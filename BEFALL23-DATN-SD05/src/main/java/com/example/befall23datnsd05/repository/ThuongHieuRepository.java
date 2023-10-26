package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.ThuongHieu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {


    @Query(value = "select * from thuong_hieu where  trang_thai=0", nativeQuery = true)
    Page<ThuongHieu> getThuongHieuByTrangThaiHoatDong(Pageable pageable);


    @Query(value = "select * from thuong_hieu where  trang_thai=1", nativeQuery = true)
    Page<ThuongHieu> getThuongHieuByTrangThaiDungHoatDong(Pageable pageable);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);


}
