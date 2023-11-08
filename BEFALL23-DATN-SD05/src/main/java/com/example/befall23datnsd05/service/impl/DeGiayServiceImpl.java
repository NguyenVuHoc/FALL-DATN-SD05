package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.repository.DeGiayRepository;
import com.example.befall23datnsd05.service.DeGiayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeGiayServiceImpl implements DeGiayService {

    @Autowired
    private DeGiayRepository repository;

    @Override
    public List<DeGiay> getAll() {
        return repository.findAll();
    }

    @Override
    public DeGiay getById(Long id) {
        Optional<DeGiay> optional = repository.findById(id);
        if (optional.isPresent()){
            return optional.get();
        }else {
            return null;
        }
    }

    @Override
    public DeGiay add(DeGiay deGiay) {
        DeGiay deGiay1 = new DeGiay();
        deGiay1.setMa(deGiay.getMa());
        deGiay1.setTen(deGiay.getTen());
        repository.save(deGiay1);
        return deGiay1;
    }

    @Override
    public DeGiay update(DeGiay deGiay) {
        DeGiay deGiay1 = repository.getReferenceById(deGiay.getId());
        if(deGiay1==null){
            return null;
        }
        deGiay1.setTen(deGiay.getTen());
        repository.save(deGiay1);
        return deGiay1;
    }

    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<DeGiay> phanTrang(Integer pageNo, Integer size) {
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

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByMa(ma);
    }

    @Override
    public boolean existsByTen(String ten) {
        return repository.existsByTen(ten);
    }

    @Override
    public boolean existsByTenAndIdNot(String ten, Long id) {
        return repository.existsByTenAndIdNot(ten, id);
    }

    @Override
    public Page<DeGiay> timTen(String ten, Integer pageNo, Integer size) {
        Pageable pageable1 = PageRequest.of(pageNo , size, Sort.by(Sort.Order.desc("id")));
        return repository.findByTenContains(ten,pageable1);
    }
}