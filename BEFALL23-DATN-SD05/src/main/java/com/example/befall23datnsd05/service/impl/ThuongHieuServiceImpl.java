package com.example.befall23datnsd05.service.impl;

import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ThuongHieuRepository;
import com.example.befall23datnsd05.request.ThuongHieuRequest;
import com.example.befall23datnsd05.service.ThuongHieuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ThuongHieuServiceImpl implements ThuongHieuService {
    private final ThuongHieuRepository repository;

    public ThuongHieuServiceImpl(ThuongHieuRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ThuongHieu> getList() {
        return repository.findAll();
    }

    @Override
    public List<ThuongHieu> getByTrangThai(TrangThai trangThai) {
        return repository.getAllByTrangThai(trangThai);
    }



    @Override
    public ThuongHieu save(ThuongHieuRequest request) {

        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setMa(request.getMa());
        thuongHieu.setTen(request.getTen());
        thuongHieu.setNgayTao(LocalDate.now());
        thuongHieu.setNgaySua(LocalDate.now());
        thuongHieu.setTrangThai(TrangThai.DANG_HOAT_DONG);
        return repository.save(thuongHieu);
    }


    @Override
    public ThuongHieu update(ThuongHieuRequest request) {
        ThuongHieu ThuongHieu1 = repository.findById(request.getId()).orElse(null);
        if (ThuongHieu1 != null) {
            ThuongHieu1.setMa(request.getMa());
            ThuongHieu1.setTen(request.getTen());
            ThuongHieu1.setNgaySua(LocalDate.now());
            ThuongHieu1.setTrangThai(request.getTrangThai());
            return repository.save(ThuongHieu1);
        }
        return null;
    }


    @Override
    public void remove(Long id) {
        repository.deleteById(id);
    }

    @Override
    public ThuongHieu findById(Long id) {
        Optional<ThuongHieu> ThuongHieu = repository.findById(id);
        if (ThuongHieu.isPresent()) {
            return ThuongHieu.get();
        }
        return null;
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
    public Integer transferPage(Integer pageNo) {
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
}
