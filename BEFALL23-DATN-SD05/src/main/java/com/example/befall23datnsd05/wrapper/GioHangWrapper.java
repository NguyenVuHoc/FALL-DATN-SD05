package com.example.befall23datnsd05.wrapper;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import lombok.Data;

import java.util.List;

@Data
public class GioHangWrapper {
    private List<GioHangChiTiet> listGioHangChiTiet;
}
