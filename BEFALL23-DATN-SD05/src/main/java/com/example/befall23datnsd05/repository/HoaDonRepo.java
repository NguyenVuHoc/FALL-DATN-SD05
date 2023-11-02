package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {

    Page<HoaDon> getAllByTrangThai(TrangThai trangThai, Pageable pageable);

    @Query("""
                SELECT hd FROM HoaDon hd
                WHERE 
                   hd.trangThai = :trangThai
            """)
    Page<HoaDon> findHoaDonsByTrangThai(
            @Param("trangThai") TrangThai trangThai,
            Pageable pageable
    );

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start AND hd.ngayTao <= :end")
    Page<HoaDon> findHoaDonsByNgayTao(LocalDate start, LocalDate end, Pageable pageable);


    @Query("SELECT hd FROM HoaDon hd WHERE" +
            " UPPER(hd.ma) LIKE %:keyword%" +
            " OR UPPER(hd.tenKhachHang) LIKE %:keyword%"
    )
    Page<HoaDon> findHoaDonsByKeyWord(String keyword, Pageable pageable);


    @Query("""
                SELECT hd FROM HoaDon hd
                WHERE (
                     UPPER(hd.ma) LIKE %:keyword%
                    OR UPPER(hd.tenKhachHang) LIKE %:keyword%
                   )
                    and
                    hd.ngayTao >= :start AND hd.ngayTao <= :end
            """)
    Page<HoaDon> findHoaDonsByKeyWordAndNgayTao(String keyword, LocalDate start, LocalDate end, Pageable pageable);


    //    Get có trạng thái
    @Query("""
                SELECT hd FROM HoaDon hd
                WHERE  hd.ngayTao >= :start AND hd.ngayTao <= :end
                AND hd.trangThai = :trangThai
            """)
    Page<HoaDon> findHoaDonsByNgayTaoAndTrangThai(LocalDate start, LocalDate end, TrangThai trangThai, Pageable pageable);


    @Query("""
                SELECT hd FROM HoaDon hd
                WHERE 
                    ( UPPER(hd.ma) LIKE %:keyword%
                    OR UPPER(hd.tenKhachHang) LIKE %:keyword%
                    )
                    AND hd.trangThai = :trangThai
            """)
    Page<HoaDon> findHoaDonsByKeyWordAndTrangThai(
            @Param("keyword") String keyword,
            @Param("trangThai") TrangThai trangThai,
            Pageable pageable
    );

    @Query("""
                SELECT hd FROM HoaDon hd
                WHERE ( UPPER(hd.ma) LIKE %:keyword%
                    OR UPPER(hd.tenKhachHang) LIKE %:keyword%)
                    AND hd.ngayTao >= :start AND hd.ngayTao <= :end
                    AND hd.trangThai = :trangThai
            """)
    Page<HoaDon> findHoaDonsByNgayTaoAndKeyWordAndTrangThai(
            @Param("start") LocalDate startDate,
            @Param("end") LocalDate endDate,
            @Param("keyword") String keyword,
            @Param("trangThai") TrangThai trangThai,
            Pageable pageable
    );


}