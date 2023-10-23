package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.dto.ChiTietSanPhamCustomerCustom;
import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.service.ChiTietSanPhamCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChiTietSanPhamCustomerServiceImpl implements ChiTietSanPhamCustomerService {

    @Autowired
    private ChiTietSanPhamRepository repository;

    @Override
    public Page<ChiTietSanPham> pageAllInShop(Integer pageNo, Integer size) {
        return repository.findAll(PageRequest.of(pageNo, size));
    }

    @Override
    public Integer nextPage(Integer pageNo) {
        Integer sizeList = repository.findAll().size();
        System.out.println(sizeList);
        Integer pageCount = (int) Math.ceil((double) sizeList / 5);
        System.out.println(pageCount);
        if (pageNo >= pageCount) {
            pageNo = 0;
        } else if (pageNo < 0) {
            pageNo = pageCount - 1;
        }
        System.out.println(pageNo);
        return pageNo;
    }

    @Override
    public List<ChiTietSanPhamCustomerCustom> list3New() {
        return repository.list3New();
    }

    @Override
    public List<ChiTietSanPhamCustomerCustom> list3Prominent() {
        return repository.list3Prominent();
    }

    @Override
    public List<ChiTietSanPhamCustomerCustom> list3Custom() {
        return repository.list3Custom();
    }

    @Override
    public List<ChiTietSanPhamCustomerCustom> list3Limited() {
        return repository.list3Limited();
    }

    @Override
    public ChiTietSanPham getById(Long id) {
        return repository.getReferenceById(id);
    }


}
