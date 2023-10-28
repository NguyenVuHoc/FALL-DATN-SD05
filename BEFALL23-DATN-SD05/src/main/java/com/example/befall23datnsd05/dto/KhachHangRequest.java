package com.example.befall23datnsd05.dto;


import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KhachHangRequest {

    private Long id;

    @NotBlank(message = "Mã không được để trống")
    private String ma;

    @NotBlank(message = "Tên không được để trống")
    @Pattern(regexp = "^[^0-9]+$", message = "Tên không được chứa số")
    private String ten;

    @NotBlank(message = "Sdt không được để trống")
    @Size(min = 10, max = 10, message = "Sdt phải đủ 10 số")
    @Pattern(regexp = "0[0-9]{9}", message = "Sdt không được chứa chữ và phải bắt đầu từ số 0")
    private String sdt;

    @NotNull(message = "Giới tính không được để trống")
    private String gioiTinh;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Vui lòng nhập Email đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;

    private TrangThai trangThai;
}
