package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.LotGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotGiayRepository extends JpaRepository<LotGiay, Long> {

    Page<LotGiay> findByTenContains(String ten, Pageable pageable);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);
}
