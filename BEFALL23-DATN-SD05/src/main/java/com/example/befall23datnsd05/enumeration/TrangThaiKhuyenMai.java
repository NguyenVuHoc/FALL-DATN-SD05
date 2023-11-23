package com.example.befall23datnsd05.enumeration;

public enum TrangThaiKhuyenMai {

    DANG_HOAT_DONG(0),
    DUNG_HOAT_DONG(1),
    SAP_DIEN_RA(2);

    private final Integer trangThai;

    TrangThaiKhuyenMai(Integer trangThai) {
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
            case SAP_DIEN_RA:
                return "Sắp diễn ra";
            default:
                return this.name(); // Returns the default enum name if no corresponding name is found
        }
    }

}
