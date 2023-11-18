package com.example.befall23datnsd05.enumeration;

public enum TrangThai {

    DANG_HOAT_DONG(0),
    DUNG_HOAT_DONG(1),
    YEU_CAU_TRA_HANG(2),
    DA_TRA_HANG(3),
    TU_CHOI_TRA_HANG(4),
    DOI_HANG(5),
    DA_DOI_HANG(6),
    TU_CHOI_DOI_HANG(7);
    private final Integer trangThai;

    TrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }

    public Integer getTrangThai() {
        return this.trangThai;
    }

    public String getDisplayName() {
        switch (this) {
            case DANG_HOAT_DONG:
                return "Đang Hoạt Động";
            case DUNG_HOAT_DONG:
                return "Dừng Hoạt Động";
            case YEU_CAU_TRA_HANG:
                return "Yêu Cầu Trả Hàng";
            case DA_TRA_HANG:
                return "Đã Trả Hàng";
            case TU_CHOI_TRA_HANG:
                return "Từ Chối Trả Hàng";
            case DOI_HANG:
                return "Đổi Hàng";
            case DA_DOI_HANG:
                return "Đã Đổi Hàng";
            case TU_CHOI_DOI_HANG:
                return "Từ Chối Đổi Hàng";
            default:
                return this.name(); // Returns the default enum name if no corresponding name is found
        }
    }

}
