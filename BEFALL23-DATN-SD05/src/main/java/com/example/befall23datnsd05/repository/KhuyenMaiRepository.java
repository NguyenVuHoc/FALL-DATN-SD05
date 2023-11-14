package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, Long> {

    @Query("""
                SELECT km FROM KhuyenMai km
                WHERE 
                   km.trangThai = :trangThai
            """)
    List<KhuyenMai> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai
    );

    @Query("select km from KhuyenMai km where km.ngayBatDau >= :start and km.ngayKetThuc <= :end")
    List<KhuyenMai> findKhuyenMaisByNgayBatDauAndNgayKetThuc(@Param("start") LocalDate start,@Param("end") LocalDate end);

    @Query("select km from KhuyenMai km where km.ngayBatDau >= :start")
    List<KhuyenMai> findKhuyenMaisByNgayBatDau(@Param("start") LocalDate start);

    @Query("select km from KhuyenMai km where km.ngayKetThuc <= :end")
    List<KhuyenMai> findKhuyenMaisByNgayKetThuc(@Param("end") LocalDate end);

    @Transactional
    @Modifying
    @Query(value = "update khuyen_mai km set km.trang_thai = 0\n" +
            "where id > 0 and current_date() between km.ngay_bat_dau and km.ngay_ket_thuc;", nativeQuery = true)
    int updateTrangThaiDangHoatDong();

    @Transactional
    @Modifying
    @Query(value = "update khuyen_mai set khuyen_mai.trang_thai = 1\n" +
            "where id > 0 and khuyen_mai.ngay_ket_thuc < current_date();", nativeQuery = true)
    int updateTrangThaiDungHoatDong1();

    @Transactional
    @Modifying
    @Query(value = "update khuyen_mai set khuyen_mai.trang_thai = 1\n" +
            "where id > 0 and date_sub(khuyen_mai.ngay_bat_dau, interval 4 day) >= current_date();", nativeQuery = true)
    int updateTrangThaiDungHoatDong2();

    @Transactional
    @Modifying
    @Query(value = "update khuyen_mai\n" +
            "set trang_thai = 10\n" +
            "where id > 0 and CURDATE() < ngay_bat_dau and CURDATE() >= ngay_bat_dau - interval 3 day;", nativeQuery = true)
    int updateTrangThaiSapDienRa();
}
