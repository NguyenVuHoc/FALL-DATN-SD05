package com.example.befall23datnsd05.enumeration;

public enum GioiTinh {

    NAM(0),
    NU(1);

    private final Integer gioiTinh;

    GioiTinh(Integer gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Integer getGioiTinh() {
        return this.gioiTinh;
    }
}
