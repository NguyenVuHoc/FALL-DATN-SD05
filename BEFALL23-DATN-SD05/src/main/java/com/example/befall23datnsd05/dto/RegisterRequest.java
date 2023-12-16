package com.example.befall23datnsd05.dto;

import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.GioiTinh;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.worker.CodeGenerator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Tên không được để trống")
    private String ten;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Vui lòng nhập Email đúng định dạng")
    private String email;


    @NotBlank(message = "Sdt không được để trống")
    @Size(min = 10, max = 10, message = "Sdt phải đủ 10 số")
    @Pattern(regexp = "0[0-9]{9}", message = "Sdt không được chứa chữ và phải bắt đầu từ số 0")
    private String sdt;

    public static KhachHang convertToEntity(RegisterRequest request){
        KhachHang khachHang = new KhachHang();
        khachHang.setMa("KH"+ CodeGenerator.genUUID());
        khachHang.setSdt(request.getSdt());
        khachHang.setTen(request.getTen());
        khachHang.setEmail(request.getEmail());
        khachHang.setNgayTao(LocalDate.now());
        khachHang.setTrangThai(TrangThai.DANG_HOAT_DONG);
        khachHang.setGioiTinh(GioiTinh.NAM);
        khachHang.setNgaySua(LocalDate.now());
        khachHang.setTichDiem(BigDecimal.valueOf(0));
        return khachHang;
    }
}
