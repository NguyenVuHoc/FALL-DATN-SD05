package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.LotGiay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotGiayRepository extends JpaRepository<LotGiay, Long> {
}
