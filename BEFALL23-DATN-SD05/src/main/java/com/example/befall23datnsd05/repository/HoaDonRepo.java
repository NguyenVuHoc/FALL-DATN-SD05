package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDon, Long> {

    List<HoaDon> getAllByTrangThai(TrangThaiDonHang trangThai);

    @Query(value = "select *from hoa_don where trang_thai = :trangThai  and id_khach_hang= :id",nativeQuery = true)
    List<HoaDon> getAllByTrangThaiAndKhachHang(@Param("trangThai") Integer trangThai,@Param("id")Long  id);

    List<HoaDon> findHoaDonByTrangThaiAndKhachHangId(TrangThaiDonHang trangThai,Long id);

    @Query("SELECT hd FROM HoaDon hd WHERE hd.ngayTao >= :start AND hd.ngayTao <= :end")
    List<HoaDon> findHoaDonsByNgayTao(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query(value = "select * from hoa_don where id_khach_hang=?1",nativeQuery = true)
    List<HoaDon> getAllByKhachHang(Long idKh);

    @Query(value = "select * from hoa_don where id_khach_hang=5",nativeQuery = true)
    List<HoaDon> getAllByKhachHangFixCung();

    @Query(value = "SELECT SUM(hoa_don.thanh_toan) FROM hoa_don WHERE trang_thai = 5;\n",nativeQuery = true)
    Long doanhThu();

    @Query(value = "SELECT COUNT(*) from hoa_don where  trang_thai=6",nativeQuery = true)
    Long soDonHuy();

}