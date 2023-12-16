package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;

public interface SendMailService {

    void sendEmail(KhachHang khachHang, String path);

    void sendEmail1(KhachHang khachHang, HoaDon hoaDon);

    boolean sendNewPassWord(String verificationPassWord, String resetPass);

    void sendNewPassWord(String mail);

    void sendDangKy(String mail);
}
