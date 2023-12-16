package com.example.befall23datnsd05.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuenMatKhauRequest {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Vui lòng nhập Email đúng định dạng")
    private String email;
}
