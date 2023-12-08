package com.example.befall23datnsd05.worker;

import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import com.example.befall23datnsd05.service.KhuyenMaiService;
import com.example.befall23datnsd05.service.MaGiamGiaService;
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

    @Autowired
    MaGiamGiaService maGiamGiaService;

    @Autowired
    ChiTietSanPhamService ctspService;

    @PostConstruct
    @Scheduled(fixedRate = 300000)
    public void autoCheckTrangThai(){
        service.updateTrangThai();
        maGiamGiaService.updateTrangThai();
    }

    @PostConstruct
    @Scheduled(fixedRate = 300000)
    public void autoCheckGiaBan(){
        ctspService.autoUpdateGia();
    }


}
