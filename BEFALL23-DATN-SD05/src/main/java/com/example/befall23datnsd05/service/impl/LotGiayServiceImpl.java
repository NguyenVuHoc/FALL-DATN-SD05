package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.repository.LotGiayRepository;
import com.example.befall23datnsd05.service.LotGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LotGiayServiceImpl implements LotGiayService {

    @Autowired
    private LotGiayRepository repository;

    @Override
    public List<LotGiay> getAll() {
        return repository.findAll();
    }

    @Override
    public LotGiay getById(Long id) {
        Optional<LotGiay> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public LotGiay add(LotGiay lotGiay) {
        LotGiay lotGiay1 = new LotGiay();
        lotGiay1.setMa(lotGiay.getMa());
        lotGiay1.setTen(lotGiay.getTen());
        repository.save(lotGiay1);
        return lotGiay1;
    }

    @Override
    public LotGiay update(LotGiay lotGiay) {
        LotGiay lotGiay1 = repository.getReferenceById(lotGiay.getId());
        if(lotGiay1==null){
            return null;
        }
        lotGiay1.setTen(lotGiay.getTen());
        repository.save(lotGiay1);
        return lotGiay1;
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<LotGiay> phanTrang(Integer pageNo, Integer size) {
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
