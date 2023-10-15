package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.MauSac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoGiayRepository extends JpaRepository<CoGiay, Long> {
}
