package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.DongSanphamCustom;
import com.example.befall23datnsd05.custom.ThuongHieuCustom;
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
    Page<DongSanphamCustom> getPageDongSanPhamCusTom(Pageable pageable);

    @Query(value = "select dong_san_pham.id,dong_san_pham.ma,dong_san_pham.ten,dong_san_pham.trang_thai from dong_san_pham  where dong_san_pham.trang_thai='0'", nativeQuery = true)
    Page<DongSanphamCustom> getDongSpByTrangThaiHoatDong(Pageable pageable);


    @Query(value = "select dong_san_pham.id,dong_san_pham.ma,dong_san_pham.ten,dong_san_pham.trang_thai from dong_san_pham  where dong_san_pham.trang_thai='1'", nativeQuery = true)
    Page<DongSanphamCustom> getDongSpByTrangThaiDungThaiHoatDong(Pageable pageable);

    boolean existsByMa(String ma);

}
