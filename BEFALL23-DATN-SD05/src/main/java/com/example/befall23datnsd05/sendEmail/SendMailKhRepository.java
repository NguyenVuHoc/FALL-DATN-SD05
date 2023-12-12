package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendMailKhRepository extends JpaRepository<KhachHang, Long> {
    public KhachHang findByEmail(String emaill);
}
