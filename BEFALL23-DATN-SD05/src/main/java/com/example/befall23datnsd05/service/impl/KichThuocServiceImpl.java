package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.repository.KichThuocRepository;
import com.example.befall23datnsd05.service.KichThuocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KichThuocServiceImpl implements KichThuocService {

    @Autowired
    private KichThuocRepository repository;

    @Override
    public List<KichThuoc> getAll() {
        return repository.findAll();
    }

    @Override
    public KichThuoc getById(Long id) {
        Optional<KichThuoc> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public KichThuoc add(KichThuoc kichThuoc) {
        KichThuoc kichThuoc1 = new KichThuoc();
        kichThuoc1.setMa(kichThuoc.getMa());
        kichThuoc1.setTen(kichThuoc.getTen());
        repository.save(kichThuoc1);
        return kichThuoc1;
    }

    @Override
    public KichThuoc update(KichThuoc kichThuoc) {
        KichThuoc kichThuoc1 = repository.getReferenceById(kichThuoc.getId());
        if(kichThuoc1==null){
            return null;
        }
        kichThuoc1.setTen(kichThuoc.getTen());
        repository.save(kichThuoc1);
        return kichThuoc1;
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<KichThuoc> phanTrang(Integer pageNo, Integer size) {
        Pageable pageable = PageRequest.of(pageNo,size, Sort.by(Sort.Order.desc("id")));
        return repository.findAll(pageable);
    }

    @Override
    public Integer chuyenPage(Integer pageNo) {
        Integer sizeList = repository.findAll().size();
        Integer pageCount = (int) Math.ceil((double) sizeList/5);
        if (pageNo >= pageCount){
            pageNo = 0;
        }else if(pageNo < 0){
            pageNo = pageCount -1;
        }
        return pageNo;
    }
}
