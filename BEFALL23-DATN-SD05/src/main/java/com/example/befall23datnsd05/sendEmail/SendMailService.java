package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;

public interface SendMailService {

    public void sendEmail(KhachHang khachHang, String path);

    public void sendEmail1(KhachHang khachHang, HoaDon hoaDon);

    public boolean verifyAccount(String verificationPassWord, String resetPass);
}
