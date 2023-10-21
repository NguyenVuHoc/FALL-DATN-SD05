package com.example.befall23datnsd05.service;

import com.example.befall23datnsd05.custom.ThuongHieuCustom;
import com.example.befall23datnsd05.entity.ThuongHieu;
import com.example.befall23datnsd05.request.ThuongHieuRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ThuongHieuService {


    List<ThuongHieu> getList();

    List<ThuongHieuCustom> getListTransfer();


    Page<ThuongHieuCustom> getPage(Integer pageNo, Integer size);

    ThuongHieu save(ThuongHieuRequest request);

    ThuongHieu update(ThuongHieuRequest request);

    void remove(Long id);

    ThuongHieu findById(Long id);

    Integer transferPage(Integer pageNo);


}
