package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.NhanVienRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.entity.NhanVien;
import com.example.befall23datnsd05.enumeration.TrangThai;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NhanVienService {

    List<NhanVien> getList();

    List<NhanVien> getByTrangThai(TrangThai trangThai);

    NhanVien add(NhanVienRequest nhanVienRequest);

    NhanVien update(NhanVienRequest nhanVienRequest);

    void remove(Long id);

    NhanVien getById(Long id);

    Page<NhanVien> timTen(String ten,Integer pageNo, Integer size);

    boolean existByMa(String ma);

    boolean existsByTen(String ten);

    boolean existsByTenAndIdNot(String ten, Long id);

    boolean changeUserPassword(Long idNv,String oldPassword, String newPassword);

}
