package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.repository.MauSacRepository;
import com.example.befall23datnsd05.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository repository;

    @Override
    public List<MauSac> getAll() {
        return repository.findAll();
    }

    @Override
    public MauSac getById(Long id) {
        Optional<MauSac> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public MauSac add(MauSac mauSac) {
        MauSac mauSac1 = new MauSac();
        mauSac1.setMa(mauSac.getMa());
        mauSac1.setTen(mauSac.getTen());
        repository.save(mauSac1);
        return mauSac1;
    }

    @Override
    public MauSac update(MauSac mauSac) {
        MauSac mauSac1 = repository.getReferenceById(mauSac.getId());
        if(mauSac1==null){
            return null;
        }
        mauSac1.setTen(mauSac.getTen());
        repository.save(mauSac1);
        return mauSac1;
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<MauSac> phanTrang(Integer pageNo, Integer size) {
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
