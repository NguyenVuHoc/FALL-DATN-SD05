package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.CoGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoGiayRepository extends JpaRepository<CoGiay, Long> {

    Page<CoGiay> findByTenContains(String ten, Pageable pageable);

    boolean existsByMa(String ma);

}
