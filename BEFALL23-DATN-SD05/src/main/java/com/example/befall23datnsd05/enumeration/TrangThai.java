package com.example.befall23datnsd05.enumeration;

public enum TrangThai {

    DANG_HOAT_DONG(0),
    DUNG_HOAT_DONG(1),
//    HOA_DON_CHO(2),
//    CHO_XAC_NHAN(3),
//    DANG_CHUAN_BI(4),
//    DANG_GIAO(5),
//    HOAN_THANH(6),
//    DA_HUY(7),
    XAC_NHAN_HOAN_TRA(8),
    DA_HOAN_TRA(9),
    SAP_DIEN_RA(10),

    YEU_CAU_TRA_HANG(2),
    DA_TRA_HANG(3),
    TU_CHOI_TRA_HANG(4),
    DOI_HANG(5),
    DA_DOI_HANG(6),
    TU_CHOI_DOI_HANG(7)
    ;
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
            case SAP_DIEN_RA:
                return "Sắp diễn ra";
            default:
                return this.name(); // Returns the default enum name if no corresponding name is found
        }
    }

//    public String getDisplayName() {
//        switch (this) {
//            case DANG_HOAT_DONG:
//                return "Đang hoạt động";
//            case DUNG_HOAT_DONG:
//                return "Dừng hoạt động";
//            case CHO_XAC_NHAN:
//                return "Chờ xác nhận";
//            case DANG_CHUAN_BI:
//                return "Đang chuẩn bị";
//            case DANG_GIAO:
//                return "Đang giao";
//            case HOAN_THANH:
//                return "Hoàn thành";
//            case DA_HUY:
//                return "Đã hủy";
//            case XAC_NHAN_HOAN_TRA:
//                return "Xác nhận hoàn trả";
//            case DA_HOAN_TRA:
//                return "Đã hoàn trả";
//            case HOA_DON_CHO:
//                return "Hoá đơn chờ";
//            case SAP_DIEN_RA:
//                return "Sắp diễn ra";
//            default:
//                return this.name(); // Returns the default enum name if no corresponding name is found
//        }
//    }

    public String getDisplayName() {
        switch (this) {
            case DANG_HOAT_DONG:
                return "Đang hoạt động";
            case DUNG_HOAT_DONG:
                return "Dừng hoạt động";
            case CHO_XAC_NHAN:
                return "Chờ xác nhận";
            case DANG_CHUAN_BI:
                return "Đang chuẩn bị";
            case DANG_GIAO:
                return "Đang giao";
            case HOAN_THANH:
                return "Hoàn thành";
            case DA_HUY:
                return "Đã hủy";
            case XAC_NHAN_HOAN_TRA:
                return "Xác nhận hoàn trả";
            case DA_HOAN_TRA:
                return "Đã hoàn trả";
            case HOA_DON_CHO:
                return "Hoá đơn chờ";
            default:
                return this.name(); // Returns the default enum name if no corresponding name is found
        }
    }

}
//=======
//}
//>>>>>>> develop
