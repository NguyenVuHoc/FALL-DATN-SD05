package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendMailNvRepository extends JpaRepository<NhanVien, Long> {
    public NhanVien findByEmail(String email);
}
