package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Long> {


    @Query("""
                SELECT th FROM ThuongHieu th
                WHERE 
                   th.trangThai = :trangThai
            """)
    List<ThuongHieu> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);


}
