package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MauSacRepository extends JpaRepository<MauSac, Long> {

    boolean existsByMa(String ma);
}
