package com.example.befall23datnsd05.enumeration;

public enum LoaiHoaDon {

    HOA_DON_OFFLINE(0),

    HOA_DON_ONLINE(1);

    private final Integer loaiHoaDon;

    LoaiHoaDon(Integer loaiHoaDon) {
        this.loaiHoaDon = loaiHoaDon;
    }

    public Integer getLoaiHoaDon() {
        return this.loaiHoaDon;
    }
}
