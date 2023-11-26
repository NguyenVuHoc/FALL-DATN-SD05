package com.example.befall23datnsd05.dto;


import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiKhuyenMai;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MaGiamGiaRequest {

    private Long id;

    private String ma;

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    private String moTa;

    @NotNull(message = "Mức giảm giá không được để trống")
    @Positive(message = "Mức giảm giá phải lớn hơn 0")
    @Max(value = 100, message = "Mức giảm giá không được vượt quá 100")
    private Integer mucGiamGia;

    @Min(value = 1, message = "Mức giảm tối đa thấp nhất là 1")
    private BigDecimal mucGiamToiDa;

    @NotNull(message = "Số lượng không được để trống")
    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Giá trị áp dụng tối thiểu không được để trống")
    @Min(value = 0, message = "Giá trị áp dụng tối thiểu là 0")
    private BigDecimal giaTriDonHang;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ngày bắt đầu phải ở hiện tại hoặc tương lai")
    private LocalDate ngayBatDau;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Ngày kết thúc phải ở hiện tại hoặc tương lai")
    private LocalDate ngayKetThuc;

    private TrangThaiKhuyenMai trangThai;

    public TrangThaiKhuyenMai htTrangThai() {
        LocalDate DaysAgo = this.ngayBatDau.minusDays(4);
        if (LocalDate.now().isEqual(ngayBatDau)) {
            return TrangThaiKhuyenMai.DANG_HOAT_DONG;
        } else if (LocalDate.now().isAfter(DaysAgo) && LocalDate.now().isBefore(ngayBatDau)) {
            return TrangThaiKhuyenMai.SAP_DIEN_RA;
        } else if (ngayBatDau.isAfter(LocalDate.now())) {
            return TrangThaiKhuyenMai.DUNG_HOAT_DONG;
        } else if (ngayKetThuc.isBefore(LocalDate.now())) {
            return TrangThaiKhuyenMai.DUNG_HOAT_DONG;
        } else if(soLuong == 0){
            return TrangThaiKhuyenMai.DUNG_HOAT_DONG;
        } else {
            return TrangThaiKhuyenMai.DANG_HOAT_DONG;
        }
    }

}
