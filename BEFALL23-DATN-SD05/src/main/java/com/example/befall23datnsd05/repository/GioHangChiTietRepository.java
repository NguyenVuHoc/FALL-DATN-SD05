package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.GioHang;
import com.example.befall23datnsd05.entity.GioHangChiTiet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, Long> {

    GioHangChiTiet findByGioHangAndChiTietSanPham(GioHang gioHang, ChiTietSanPham chiTietSanPham);
}
