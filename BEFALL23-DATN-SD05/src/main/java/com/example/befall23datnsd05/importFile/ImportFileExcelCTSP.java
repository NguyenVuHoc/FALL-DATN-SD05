package com.example.befall23datnsd05.importFile;

import com.example.befall23datnsd05.entity.ChiTietSanPham;
import com.example.befall23datnsd05.entity.CoGiay;
import com.example.befall23datnsd05.entity.DeGiay;
import com.example.befall23datnsd05.entity.KichThuoc;
import com.example.befall23datnsd05.entity.LotGiay;
import com.example.befall23datnsd05.entity.MauSac;
import com.example.befall23datnsd05.entity.SanPham;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.ChiTietSanPhamRepository;
import com.example.befall23datnsd05.repository.CoGiayRepository;
import com.example.befall23datnsd05.repository.DeGiayRepository;
import com.example.befall23datnsd05.repository.KichThuocRepository;
import com.example.befall23datnsd05.repository.LotGiayRepository;
import com.example.befall23datnsd05.repository.MauSacRepository;
import com.example.befall23datnsd05.repository.SanPhamRepository;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImportFileExcelCTSP {

    @Autowired
    private ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private MauSacRepository mauSacRepository;

    @Autowired
    private KichThuocRepository kichThuocRepository;

    @Autowired
    private DeGiayRepository deGiayRepository;

    @Autowired
    private CoGiayRepository coGiayRepository;

    @Autowired
    private LotGiayRepository lotGiayRepository;

    public void ImportFile(String path) {
        try {
            List<ChiTietSanPham> listCTSP = new ArrayList<>();
            FileInputStream fileExcel = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(fileExcel);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            Row firtRow = iterator.next();
            Cell firtCell = firtRow.getCell(0);
            while (iterator.hasNext()) {
                Row row = iterator.next();
                String sanPhamStr = String.valueOf(getCellValue(row.getCell(1))).trim();
                String mauSacStr = String.valueOf(getCellValue(row.getCell(2))).trim();
                String kichThuocStr = String.valueOf(getCellValue(row.getCell(3))).trim();
                String deGiayStr = String.valueOf(getCellValue(row.getCell(4))).trim();
                String coGiayStr = String.valueOf(getCellValue(row.getCell(5))).trim();
                String lotGiayStr = String.valueOf(getCellValue(row.getCell(6))).trim();
                String soLuongTon = String.valueOf(getCellValue(row.getCell(7))).trim();
                String giaBan = String.valueOf(getCellValue(row.getCell(8))).trim();

                if (mauSacStr.isEmpty() && sanPhamStr.isEmpty() && kichThuocStr.isEmpty() && deGiayStr.isEmpty()
                        && coGiayStr.isEmpty() && lotGiayStr.isEmpty() && soLuongTon.isEmpty() && giaBan.isEmpty()) {
                    return;
                }

                SanPham sanPham = sanPhamRepository.findSanPhamByTen(sanPhamStr).get();
                if (sanPham == null) {
                    return;
                }

                MauSac mauSac = mauSacRepository.findMauSacByTen(mauSacStr).get();
                if (mauSac == null) {
                    return;
                }

                KichThuoc kichThuoc = kichThuocRepository.findKichThuocByTen(kichThuocStr).get();
                if (kichThuoc == null) {
                    return;
                }

                DeGiay deGiay = deGiayRepository.findDeGiayByTen(deGiayStr).get();
                if (deGiay == null) {
                    return;
                }

                CoGiay coGiay = coGiayRepository.findCoGiayByTen(coGiayStr).get();
                if (coGiay == null) {
                    return;
                }

                LotGiay lotGiay = lotGiayRepository.findLotGiayByTen(lotGiayStr).get();
                if (lotGiay == null) {
                    return;
                }

                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                ChiTietSanPham chiTietSanPhamCheck = chiTietSanPhamRepository.findChiTietSanPham(sanPham.getTen(), mauSac.getTen(), deGiay.getTen(), coGiay.getTen(), lotGiay.getTen(), kichThuoc.getTen()).get();
                if (chiTietSanPhamCheck == null) {
                    chiTietSanPham.setSanPham(sanPham);
                    chiTietSanPham.setMauSac(mauSac);
                    chiTietSanPham.setKichThuoc(kichThuoc);
                    chiTietSanPham.setDeGiay(deGiay);
                    chiTietSanPham.setCoGiay(coGiay);
                    chiTietSanPham.setLotGiay(lotGiay);
                    chiTietSanPham.setSoLuongTon(Integer.parseInt(soLuongTon));
                    chiTietSanPham.setGiaBan(BigDecimal.valueOf(Double.valueOf(giaBan)));
                    chiTietSanPham.setTrangThai(TrangThai.DANG_HOAT_DONG);
                    chiTietSanPham.setNgayTao(LocalDate.now());
                    chiTietSanPhamService.save(chiTietSanPham);
                } else {
                    chiTietSanPhamCheck.setSoLuongTon(chiTietSanPhamCheck.getSoLuongTon() + Integer.parseInt(soLuongTon));
                    chiTietSanPhamService.save(chiTietSanPhamCheck);
                }
                workbook.close();
            }
        } catch (Exception e) {

        }
    }

    private static Object getCellValue(Cell cell) {
        try {
            switch (cell.getCellType()) {
                case NUMERIC -> {
                    return cell.getNumericCellValue();
                }
                case BOOLEAN -> {
                    return cell.getBooleanCellValue();
                }
                default -> {
                    return cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            return "";
        }
    }

}
