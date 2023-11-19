package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.AnhSanPham;
import com.example.befall23datnsd05.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnhSanPhamRepository extends JpaRepository<AnhSanPham, Long> {

    List<AnhSanPham> findBySanPham(SanPham sanPham);

    List<AnhSanPham> findBySanPhamId(Long id);
}
