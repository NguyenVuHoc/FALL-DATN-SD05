package com.example.befall23datnsd05.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KhachHangRestController {

    @RequestMapping(value = "/admin/khach-hang/add-dia-chi/check-trong", method = {RequestMethod.GET, RequestMethod.POST})
    public String checkTrong(@RequestParam("tenNguoiNhan") String tenNguoiNhan,
                             @RequestParam("sdt") String sdt,
                             @RequestParam("diaChi") String diaChi,
                             @RequestParam("ghiChu") String ghiChu
    ) {
        if (tenNguoiNhan.equals("")) {
            return "tenNguoiNhanNull";
        } else if(sdt.equals("")) {
            return "sdtNull";
        } else if(diaChi.equals("")) {
            return "diaChiNull";
        } else if(ghiChu.equals("")) {
            return "ghiChuNull";
        }else {
            return "OK";
        }
    }



}
