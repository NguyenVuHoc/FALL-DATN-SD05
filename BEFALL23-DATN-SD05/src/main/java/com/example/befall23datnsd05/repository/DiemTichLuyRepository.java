package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.DiemTichLuy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiemTichLuyRepository extends JpaRepository<DiemTichLuy, Long> {
}
