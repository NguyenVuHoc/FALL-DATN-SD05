package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.entity.MauSac;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KichThuocRepository extends JpaRepository<KichThuoc, Long> {

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);
    @Query(value = "select * from kich_thuoc where kich_thuoc.ten like :kichThuoc", nativeQuery = true)
    Optional<KichThuoc> findKichThuocByTen(@Param("kichThuoc") String mauSac);
}
