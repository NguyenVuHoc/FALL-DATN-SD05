package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {

    List<HoaDon> getAllByTrangThai(TrangThai trangThai);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start AND hd.ngayTao <= :end")
    List<HoaDon> findHoaDonsByNgayTao(@Param("start") LocalDate start, @Param("end") LocalDate end);



}