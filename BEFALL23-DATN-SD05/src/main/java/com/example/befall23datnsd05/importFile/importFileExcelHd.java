package com.example.befall23datnsd05.importFile;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.enumeration.LoaiHoaDon;
import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.service.HoaDonService;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
public class importFileExcelHd {
    private final HoaDonService hoaDonService;
    private final GioHangChiTietRepository gioHangChiTietRepository;
    public importFileExcelHd(HoaDonService hoaDonService, GioHangChiTietRepository gioHangChiTietRepository) {
        this.hoaDonService = hoaDonService;
        this.gioHangChiTietRepository = gioHangChiTietRepository;
    }

    public ResponseEntity<byte[]> createExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sản phẩm");
        Row headerRow = sheet.createRow(0);
        int stt=1;
        String[] columns = {"STT","ID", "Mã", "Ghi chú", "Loại HD", "Ngày Giao", "Ngày Thanh Toán", "Phí Vận Chuyển",
                "SDT","Tên Khách Hàng","Thanh Toán","Tổng Tiền","Trạng Thái Đơn HÀng","Xu","Mã Giảm Giá","Tên Nhân Viên"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<HoaDon> hoaDons = hoaDonService.getAll();
        int rowNum = 1;
        for( HoaDon item: hoaDons ){
            int columnNum = 0; // Initialize column index
            Row row = sheet.createRow(rowNum++);
            row.createCell(columnNum++).setCellValue(stt++);
            row.createCell(columnNum++).setCellValue(item.getId());
            row.createCell(columnNum++).setCellValue(item.getMa());
            row.createCell(columnNum++).setCellValue(item.getGhiChu());
            row.createCell(columnNum++).setCellValue(this.getDisplayNameLHD(item.getLoaiHoaDon()));
            if (item.getNgayGiao() != null) {
                row.createCell(columnNum++).setCellValue(item.getNgayGiao().toString());
            } else {
                row.createCell(columnNum++).setCellValue("Ngày giao không xác định");
            }
            if (item.getNgayThanhToan() != null) {
                row.createCell(columnNum++).setCellValue(item.getNgayThanhToan().toString());
            } else {
                row.createCell(columnNum++).setCellValue("Ngày thanh toán không xác định");
            }

            if(item.getPhiVanChuyen()!=null){
                row.createCell(columnNum++).setCellValue(formatMoney(item.getPhiVanChuyen()));
            }
            else {
                row.createCell(columnNum++).setCellValue("0 VND");
            }
            row.createCell(columnNum++).setCellValue(item.getSdt());
            row.createCell(columnNum++).setCellValue(item.getTenKhachHang());
            if (item.getThanhToan() != null) {
                row.createCell(columnNum++).setCellValue(formatMoney(item.getThanhToan()));
            } else {
                row.createCell(columnNum++).setCellValue("Chưa thanh toán");
            }

            if (item.getTongTien() != null) {
                row.createCell(columnNum++).setCellValue(formatMoney(item.getTongTien()));
            } else {
                row.createCell(columnNum++).setCellValue("Giá trị không khả dụng");
            }

                row.createCell(columnNum++).setCellValue(this.getDisplayNameTTDH(item.getTrangThai()));

            if (item.getXu() != null) {
                row.createCell(columnNum++).setCellValue(item.getXu().stripTrailingZeros().toString());
            } else {
                row.createCell(columnNum++).setCellValue("0");
            }

            if(item.getMaGiamGia()!=null){
                row.createCell(columnNum++).setCellValue(item.getMaGiamGia().getTen() != null ? item.getMaGiamGia().getTen() : "");
            }
            else {
                row.createCell(columnNum++).setCellValue("Không áp dụng");
            }
            if(item.getNhanVien()!=null){
                row.createCell(columnNum++).setCellValue(item.getNhanVien().getTen() != null ? item.getNhanVien().getTen() : "");
            }
            else {
                row.createCell(columnNum++).setCellValue("");
            }

        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();

            byte[] excelBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "hoa_don_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    public ResponseEntity<byte[]> createExcelHoanTra() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sản phẩm");
        Row headerRow = sheet.createRow(0);
        int stt=1;
        String[] columns = {"STT","ID", "Mã", "Tên SP","Số Lượng","Đơn Giá","Trạng Thái","MÃ HD"};
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
        List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findByHoaDonHoanTraHoanLaiKho1();
        int rowNum = 1;
        for( GioHangChiTiet item: gioHangChiTiets ){
            int columnNum = 0; // Initialize column index
            Row row = sheet.createRow(rowNum++);
            row.createCell(columnNum++).setCellValue(stt++);
            row.createCell(columnNum++).setCellValue(item.getId());
            row.createCell(columnNum++).setCellValue(item.getChiTietSanPham().getSanPham().getMa());
            row.createCell(columnNum++).setCellValue(item.getChiTietSanPham().getSanPham().getTen());
            row.createCell(columnNum++).setCellValue(item.getSoLuong());
            row.createCell(columnNum++).setCellValue(formatMoney(item.getDonGia()));
            row.createCell(columnNum++).setCellValue(this.getDisplayName(item.getTrangThai()));
            row.createCell(columnNum++).setCellValue(item.getHoaDon().getMa());
        }

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            workbook.close();

            byte[] excelBytes = byteArrayOutputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDispositionFormData("attachment", "san_pham_hoan_tra_data.xlsx");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelBytes);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }


    public String getDisplayNameTTDH(TrangThaiDonHang trangThaiDonHang) {
        switch (trangThaiDonHang) {
            case HOA_DON_CHO:
                return "Hoá Đơn Chờ";
            case CHO_XAC_NHAN:
                return "Chờ Xác Nhận";
            case DANG_CHUAN_BI:
                return "Đang Chuẩn Bị";
            case DANG_GIAO:
                return "Đang Giao";
            case DA_GIAO:
                return "Đã Giao";
            case HOAN_THANH:
                return "Hoàn Thành";
            case DA_HUY:
                return "Đã Hủy";
            case DA_TRA_HANG:
                return "Đã Hoàn Trả ";
            case XAC_NHAN_TRA_HANG:
                return "Xác Nhận Hoàn Trả";
            case DOI_HANG:
                return "Đổi Hàng";
            default:
                return trangThaiDonHang.name(); // Returns the default enum name if no corresponding name is found
        }
    }
    public String getDisplayNameLHD(LoaiHoaDon loaiHoaDon) {
        switch (loaiHoaDon) {
            case HOA_DON_ONLINE:
                return "Mua Hàng Online";
            case HOA_DON_OFFLINE:
                return "Mua Hàng Offline";

            default:
                return loaiHoaDon.name(); // Returns the default enum name if no corresponding name is found
        }
    }
    public static String formatMoney(BigDecimal amount) {
        DecimalFormat formatter = new DecimalFormat("#,### VND");
        return formatter.format(amount);
    }
    public String getDisplayName(TrangThai trangThai) {
        switch (trangThai) {
            case DANG_HOAT_DONG:
                return "Đang Hoạt Động";
            case DUNG_HOAT_DONG:
                return "Dừng Hoạt Động";
            case YEU_CAU_TRA_HANG:
                return "Yêu Cầu Hoàn Trả";
            case DA_TRA_HANG:
                return "Đã Hoàn Trả";
            case TU_CHOI_TRA_HANG:
                return "Từ Chối Hoàn Trả";
            case DOI_HANG:
                return "Đổi Hàng";
            case DA_DOI_HANG:
                return "Đã Đổi Hàng";
            case TU_CHOI_DOI_HANG:
                return "Từ Chối Đổi Hàng";
            case SAP_DIEN_RA:
                return "Sắp diễn ra";
            case XAC_NHAN_HOAN_TRA:
                return "Xác nhận hoàn trả";
            case DA_HOAN_TRA:
                return "Đã hoàn trả";
            default:
                return trangThai.name(); // Returns the default enum name if no corresponding name is found
        }
    }
}
