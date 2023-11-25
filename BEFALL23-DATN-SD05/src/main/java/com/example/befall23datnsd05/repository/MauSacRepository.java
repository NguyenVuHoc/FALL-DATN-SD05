package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {

    boolean existsByMa(String ma);

    @Query(value = "select * from mau_sac where mau_sac.ten like :mauSac", nativeQuery = true)
    Optional<MauSac> findMauSacByTen(@Param("mauSac") String mauSac);
}
