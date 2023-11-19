package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.DongSanphamCustom;
import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.DongSanPham;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DongSanPhamRepository extends JpaRepository<DongSanPham, Long> {



    @Query("""
                SELECT dsp FROM DongSanPham dsp
                WHERE 
                   dsp.trangThai = :trangThai
            """)
    List<DongSanPham> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai
    );

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);


    boolean existsByTenAndIdNot(String ten, Long id);

}
