package com.example.befall23datnsd05.request;

import com.example.befall23datnsd05.enumeration.TrangThai;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.EnumType.ORDINAL;
@Getter
@Setter
@ToString
public class HoaDonRequest {
    Long id;
    @Column(name = "trang_thai")
    @Enumerated(ORDINAL)
    private TrangThai trangThai;
}
