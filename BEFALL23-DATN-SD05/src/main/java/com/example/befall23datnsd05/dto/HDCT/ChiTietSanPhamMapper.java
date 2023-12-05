package com.example.befall23datnsd05.dto.HDCT;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public class ChiTietSanPhamMapper {
    public static ChiTietSanPhamDTO toDTO(ChiTietSanPham entity) {
        ChiTietSanPhamDTO dto = new ChiTietSanPhamDTO();
        dto.setId(entity.getId());
        dto.setTenSanPham(entity.getSanPham().getTen());
        dto.setTenDongSanPham(entity.getSanPham().getDongSanPham().getTen());
        dto.setTenThuongHieu(entity.getSanPham().getThuongHieu().getTen());
        dto.setTenDeGiay(entity.getDeGiay().getTen());
        dto.setTenMauSac(entity.getMauSac().getTen());
        dto.setTenKichThuoc(entity.getKichThuoc().getTen());
        dto.setTenLotGiay(entity.getLotGiay().getTen());
        dto.setTenCoGiay(entity.getCoGiay().getTen());
        dto.setKhuyenMai(entity.getKhuyenMai());
        dto.setGiaBan(entity.getGiaBan());
        dto.setGiaMacDinh(entity.getGiaMacDinh());
        dto.setTenTrangThai(entity.getTrangThai());
        dto.setNgayTao(entity.getNgayTao());
        dto.setSoLuongTon(entity.getSoLuongTon());
        return dto;
    }

    public static Page<ChiTietSanPhamDTO> toDTOPage(Page<ChiTietSanPham> entityPage) {
        List<ChiTietSanPhamDTO> dtoList = entityPage.getContent().stream()
                .map(ChiTietSanPhamMapper::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
