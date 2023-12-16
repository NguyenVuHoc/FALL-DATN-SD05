package com.example.befall23datnsd05.importFile;

import com.example.befall23datnsd05.entity.*;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.repository.*;
import com.example.befall23datnsd05.service.ChiTietSanPhamService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileExcelCTSP {

    @Autowired
    ChiTietSanPhamService chiTietSanPhamService;

    @Autowired
    SanPhamRepository sanPhamRepository;

    @Autowired
    MauSacRepository mauSacRepository;

    @Autowired
    KichThuocRepository kichThuocRepository;

    @Autowired
    DeGiayRepository deGiayRepository;

    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    LotGiayRepository lotGiayRepository;

    @Autowired
    CoGiayRepository coGiayRepository;

    Integer indexLoi = 0;

    public void ImportFile(
            String path, SanPhamRepository sanPhamRepository, MauSacRepository mauSacRepository,
            KichThuocRepository kichThuocRepository, DeGiayRepository deGiayRepository,
            ChiTietSanPhamRepository chiTietSanPhamRepository, ChiTietSanPhamService chiTietSanPhamService,
            LotGiayRepository lotGiayRepository, CoGiayRepository coGiayRepository) throws Exception {

        FileInputStream fileExcel = new FileInputStream(new File(path));
        Workbook workbook = new XSSFWorkbook(fileExcel);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        Row firtRow = iterator.next();
        Cell firtCell = firtRow.getCell(0);
        List<Integer> listIndex = new ArrayList<>();
        int index = 0;
        while (iterator.hasNext()) {
            index++;
            try {
                Row row = iterator.next();
                String sanPhamStr = String.valueOf(getCellValue(row.getCell(0))).trim();
                String mauSacStr = String.valueOf(getCellValue(row.getCell(1))).trim();
                String kichThuocStr = String.valueOf(getCellValue(row.getCell(2))).trim().replace(".0", "");
                String deGiayStr = String.valueOf(getCellValue(row.getCell(5))).trim();
                String coGiayStr = String.valueOf(getCellValue(row.getCell(4))).trim();
                String lotGiayStr = String.valueOf(getCellValue(row.getCell(3))).trim();
                String soLuongTon = String.valueOf(getCellValue(row.getCell(6))).trim().replace(".0", "");
                String giaMacDinh = String.valueOf(getCellValue(row.getCell(7))).trim();

                if (mauSacStr.isEmpty() && sanPhamStr.isEmpty() && kichThuocStr.isEmpty() && deGiayStr.isEmpty()
                        && soLuongTon.isEmpty() && giaMacDinh.isEmpty() && coGiayStr.isEmpty() && lotGiayStr.isEmpty()) {
                    listIndex.add(index);
                    continue;
                }

                SanPham sanPham = sanPhamRepository.findSanPhamByTen(sanPhamStr)
                        .orElse(null);
                MauSac mauSac = mauSacRepository.findMauSacByTen(mauSacStr)
                        .orElse(null);;
                KichThuoc kichThuoc = kichThuocRepository.findKichThuocByTen(kichThuocStr)
                        .orElse(null);;
                DeGiay deGiay = deGiayRepository.findDeGiayByTen(deGiayStr)
                        .orElse(null);;
                CoGiay coGiay = coGiayRepository.findCoGiayByTen(coGiayStr)
                        .orElse(null);;
                LotGiay lotGiay = lotGiayRepository.findLotGiayByTen(lotGiayStr)
                        .orElse(null);;

                if (sanPham == null || mauSac == null || kichThuoc == null || deGiay == null || coGiay == null || lotGiay == null) {
                    listIndex.add(index);
                    continue;
                }
                if (Integer.parseInt(soLuongTon) < 0 || new BigDecimal(giaMacDinh).compareTo(BigDecimal.ZERO) < 0) {
                    listIndex.add(index);
                    continue;
                }

                ChiTietSanPham chiTietSanPham = new ChiTietSanPham();
                ChiTietSanPham chiTietSanPhamCheck = chiTietSanPhamRepository.findChiTietSanPham(sanPham.getTen(), mauSac.getTen(), deGiay.getTen(), coGiay.getTen(), lotGiay.getTen(), kichThuoc.getTen())
                        .orElse(null);;
                if (chiTietSanPhamCheck == null) {
                    chiTietSanPham.setSanPham(sanPham);
                    chiTietSanPham.setMauSac(mauSac);
                    chiTietSanPham.setKichThuoc(kichThuoc);
                    chiTietSanPham.setDeGiay(deGiay);
                    chiTietSanPham.setCoGiay(coGiay);
                    chiTietSanPham.setLotGiay(lotGiay);
                    chiTietSanPham.setSoLuongTon(Integer.parseInt(soLuongTon));
                    chiTietSanPham.setGiaBan(BigDecimal.valueOf(Double.parseDouble(giaMacDinh)));
                    chiTietSanPham.setGiaMacDinh(BigDecimal.valueOf(Double.parseDouble(giaMacDinh)));
                    chiTietSanPham.setTrangThai(TrangThai.DANG_HOAT_DONG);
                    chiTietSanPham.setNgayTao(LocalDate.now());
                    chiTietSanPhamService.save(chiTietSanPham);
                } else {
                    chiTietSanPhamCheck.setSoLuongTon(chiTietSanPhamCheck.getSoLuongTon() + Integer.parseInt(soLuongTon));
                    chiTietSanPhamCheck.setGiaMacDinh(BigDecimal.valueOf(Double.parseDouble(giaMacDinh)));
                    chiTietSanPhamCheck.setGiaBan(chiTietSanPhamCheck.tinhGiaSauGiamGia());
                    chiTietSanPhamService.save(chiTietSanPhamCheck);
                }
                workbook.close();

            } catch (Exception e) {
                e.printStackTrace();
                listIndex.add(index);
                continue;
            }
        }
        this.SaveFileError(path, listIndex);
        indexLoi = listIndex.size();
    }

    public Integer checkLoi() {
        return indexLoi;
    }

    public void SaveFileError(String path, List<Integer> listIndex) {
        try (FileInputStream fileExcel = new FileInputStream(new File(path))) {
            Workbook workbook = new XSSFWorkbook(fileExcel);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            int rowIndex = 0;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                for (Integer index : listIndex) {
                    if (rowIndex == index) {
                        colorRowRed(workbook, currentRow);
                        break;
                    }
                }
                rowIndex++;
            }

            try (FileOutputStream fileOut = new FileOutputStream(path)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void colorRowRed(Workbook workbook, Row row) {
        CellStyle redCellStyle = createRedCellStyle(workbook);

        int lastCellNum = row.getLastCellNum();
        for (int i = 0; i < lastCellNum; i++) {
            Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellStyle(redCellStyle);
        }
    }

    private static CellStyle createRedCellStyle(Workbook workbook) {
        CellStyle redCellStyle = workbook.createCellStyle();
        redCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        redCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return redCellStyle;
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

    public ResponseEntity<byte[]> createExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sản phẩm");
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Tên sản phẩm", "Màu sắc", "Kích cỡ", "Lót giày", "Cổ giày", "Loại đế", "Số lượng", "Giá"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<ChiTietSanPham> chiTietSanPhams = chiTietSanPhamService.getAll();
        int rowNum = 1;
        for( ChiTietSanPham item: chiTietSanPhams ){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getSanPham().getTen());
            row.createCell(1).setCellValue(item.getMauSac().getTen());
            row.createCell(2).setCellValue(item.getKichThuoc().getTen());
            row.createCell(3).setCellValue(item.getLotGiay().getTen());
            row.createCell(4).setCellValue(item.getCoGiay().getTen());
            row.createCell(5).setCellValue(item.getDeGiay().getTen());
            row.createCell(6).setCellValue(item.getSoLuongTon());
            row.createCell(7).setCellValue(item.getGiaBan().toString());

        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();

            byte[] excelBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "product_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    public ResponseEntity<byte[]> createExcelTemplate() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sản phẩm");
        Row headerRow = sheet.createRow(0);
        String[] columns = {"Tên sản phẩm", "Màu sắc", "Kích cỡ", "Lót giày", "Cổ giày", "Loại đế", "Số lượng", "Giá"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();

            byte[] excelBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "excel_template.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }



}
