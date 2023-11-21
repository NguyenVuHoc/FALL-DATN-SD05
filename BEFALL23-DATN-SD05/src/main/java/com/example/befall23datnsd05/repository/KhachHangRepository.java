package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Long> {

    @Query("""
                SELECT kh FROM KhachHang kh
                WHERE 
                   kh.trangThai = :trangThai and kh.ten not in ('Khach le')
            """)
    List<KhachHang> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai
    );

    @Query(value = "SELECT *\n" +
            "FROM khach_hang\n" +
            "WHERE id NOT IN (1);", nativeQuery = true)
    List<KhachHang> getListKhachHang();

    boolean existsBySdt(String sdt);
    boolean existsBySdtAndIdNot(String sdt, Long id);
}
