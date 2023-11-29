package com.example.befall23datnsd05.repository;

import com.example.befall23datnsd05.entity.DeGiay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeGiayRepository extends JpaRepository<DeGiay, Long> {

    Page<DeGiay> findByTenContains(String ten, Pageable pageable);

    boolean existsByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);

    @Query(value = "select * from de_giay where de_giay.ten like :ten", nativeQuery = true)
    Optional<DeGiay> findDeGiayByTen(@Param("ten") String ten);
}
