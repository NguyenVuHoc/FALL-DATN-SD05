package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {
    @Query(value = "select * from gio_hang_chi_tiet where id_hoa_don=?1",nativeQuery = true)
    List<GioHangChiTiet> findByHoaDon(long id);

    GioHangChiTiet findGioHangChiTietById(Long id);

}
