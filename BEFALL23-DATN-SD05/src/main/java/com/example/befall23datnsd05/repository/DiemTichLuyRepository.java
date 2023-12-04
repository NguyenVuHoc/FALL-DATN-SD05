package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.DiemTichLuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiemTichLuyRepository extends JpaRepository<DiemTichLuy, Long> {

    @Query("""
                SELECT d.diem FROM DiemTichLuy d
                WHERE\s
                   d.khachHang.id = :id
            """)

    int getDiemTichLuyByIdKhach(Long id);

    Optional<DiemTichLuy> findDiemTichLuyByKhachHangId(Long id);
}
