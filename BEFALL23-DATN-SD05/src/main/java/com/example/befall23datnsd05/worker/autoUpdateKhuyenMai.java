package com.example.befall23datnsd05.worker;

import com.example.befall23datnsd05.entity.KhuyenMai;
import com.example.befall23datnsd05.service.KhuyenMaiService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class autoUpdateKhuyenMai {

    @Autowired
    KhuyenMaiService service;

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoCheckTrangThai(){
        service.updateTrangThai();
    }
}
