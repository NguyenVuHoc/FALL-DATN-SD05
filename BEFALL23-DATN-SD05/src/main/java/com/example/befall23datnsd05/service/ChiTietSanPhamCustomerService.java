package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.dto.AnhCustomerCustom;
import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.dto.HDCT.ChiTietSanPhamDTO;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ChiTietSanPhamCustomerService {

    Page<ChiTietSanPham> pageAllInShop(Integer pageNo, Integer size);

    Integer nextPage(Integer pageNo);

    List<ChiTietSanPhamCustomerCustom> list3New();

    List<ChiTietSanPhamCustomerCustom> list3Prominent();

    List<ChiTietSanPhamCustomerCustom> list3Custom();

    List<ChiTietSanPhamCustomerCustom> list3Limited();

    List<ChiTietSanPhamCustomerCustom> list4Random();

    List<AnhCustomerCustom> listAnhDetail(Long id);

    ChiTietSanPham getById(Long id);

    public ChiTietSanPhamDTO convertToDTO(ChiTietSanPham chiTietSanPham);
    public List<ChiTietSanPhamDTO> convertToDTOList(List<ChiTietSanPham> chiTietSanPhamList);
    Page<ChiTietSanPhamDTO> findAllByCondition(List<String> tenThuongHieu, List<String> tenDongSanPham, List<String> tenKichThuoc, List<String> tenLotGiay, List<String> tenCoGiay, List<String> tenDeGiay, List<String> tenMauSac, Double minGia, Double maxGia, int page, int pageSize, String sortField);
}
