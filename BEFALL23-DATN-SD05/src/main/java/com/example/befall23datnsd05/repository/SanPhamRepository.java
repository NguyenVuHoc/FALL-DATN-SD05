package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.custom.SanPhamCustom;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.request.SanPhamRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {

    @Query("""
                SELECT sp FROM SanPham sp
                WHERE 
                   sp.trangThai = :trangThai
            """)
    List<SanPhamCustom> getAllByTrangThai(
            @Param("trangThai") TrangThai trangThai);

    @Query(value = "select p from SanPham p", nativeQuery = false)
    List<SanPhamCustom> getPageSanPhamCusTom();

    @Query(value = "select p from SanPham p where p.id=?1", nativeQuery = false)
    Optional<SanPhamRequest> findById1(Long id);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);

}
