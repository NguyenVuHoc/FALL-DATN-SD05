package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.MaGiamGia;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaGiamGiaRepository extends JpaRepository<MaGiamGia, Long> {

    @Query("""
                SELECT mgg FROM MaGiamGia mgg
                WHERE 
                   mgg.trangThai = :trangThai
            """)
    List<MaGiamGia> getAllByTrangThai(
            @Param("trangThai") TrangThaiKhuyenMai trangThaiKhuyenMai
    );

    @Query(value = "select * from ma_giam_gia\n" +
            "where ma_giam_gia.trang_thai = 0;", nativeQuery = true)
    List<MaGiamGia> listMaGiamGiaHoatDong();

    @Query("select mgg from MaGiamGia mgg where mgg.ngayBatDau >= :start and mgg.ngayKetThuc <= :end")
    List<MaGiamGia> findMaGiamGiasByByNgayBatDauAndNgayKetThuc(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Transactional
    @Modifying
    @Query(value = "update ma_giam_gia mgg set mgg.trang_thai = 0\n" +
            "where mgg.id > 0 and mgg.so_luong > 0  and current_date() between mgg.ngay_bat_dau and mgg.ngay_ket_thuc;", nativeQuery = true)
    int updateTrangThaiDangHoatDong();

    @Transactional
    @Modifying
    @Query(value = "update ma_giam_gia mgg set mgg.trang_thai = 1\n" +
            "where mgg.id > 0 and (mgg.so_luong <= 0  or mgg.ngay_ket_thuc < current_date());", nativeQuery = true)
    int updateTrangThaiDungHoatDong1();

    @Transactional
    @Modifying
    @Query(value = "update ma_giam_gia mgg set mgg.trang_thai = 1\n" +
            "where mgg.id > 0 and  date_sub(mgg.ngay_bat_dau, interval 4 day) >= current_date();", nativeQuery = true)
    int updateTrangThaiDungHoatDong2();

    @Transactional
    @Modifying
    @Query(value = "update ma_giam_gia mgg set mgg.trang_thai = 2\n" +
            "where mgg.id > 0 and CURDATE() < mgg.ngay_bat_dau and CURDATE() >= mgg.ngay_bat_dau - interval 3 day and mgg.ngay_bat_dau <= mgg.ngay_ket_thuc;", nativeQuery = true)
    int updateTrangThaiSapDienRa();
}
