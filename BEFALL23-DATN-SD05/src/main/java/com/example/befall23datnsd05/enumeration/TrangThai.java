package com.example.befall23datnsd05.enumeration;

public enum TrangThai {

    DANG_HOAT_DONG(0),
    DUNG_HOAT_DONG(1),
    HOA_DON_CHO(2),
    CHO_XAC_NHAN(3),
    DANG_CHUAN_BI(4),
    DANG_GIAO(5),
    HOAN_THANH(6),
    DA_HUY(7),
    XAC_NHAN_HOAN_TRA(8),
    DA_HOAN_TRA(9);

    private final Integer trangThai;

    TrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

}
