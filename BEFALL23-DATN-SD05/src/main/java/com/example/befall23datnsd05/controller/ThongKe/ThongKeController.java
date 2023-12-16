package com.example.befall23datnsd05.controller.ThongKe;

import com.example.befall23datnsd05.enumeration.TrangThai;
import com.example.befall23datnsd05.service.GioHangChiTietService;
import com.example.befall23datnsd05.service.HoaDonChiTietService;
import com.example.befall23datnsd05.service.HoaDonService;
import com.example.befall23datnsd05.service.ThongKeService;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/thong-ke")
public class ThongKeController {
    private final ThongKeService thongKeService;
    private final HoaDonService hoaDonService;
    private final HoaDonChiTietService hoaDonChiTietService;
    private final GioHangChiTietService gioHangChiTietService;
    private final com.example.befall23datnsd05.importFile.importFileExcelHd importFileExcelHd;


    public ThongKeController(ThongKeService thongKeService, HoaDonService hoaDonService, HoaDonChiTietService hoaDonChiTietService, GioHangChiTietService gioHangChiTietService, com.example.befall23datnsd05.importFile.importFileExcelHd importFileExcelHd) {
        this.thongKeService = thongKeService;
        this.hoaDonService = hoaDonService;
        this.hoaDonChiTietService = hoaDonChiTietService;
        this.gioHangChiTietService = gioHangChiTietService;
        this.importFileExcelHd = importFileExcelHd;
    }

    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    /**
     * Thống Kê
     * @param model
     * @param from
     * @param to
     * @return
     */
    @GetMapping()
    public String hienThi(Model model,
                          @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                          @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        from = (from != null) ? from : LocalDate.of(2000, 1, 1);
        to = (to != null) ? to : LocalDate.now();
        //thống kê Hoá Đơn
        List<Object[]> listHdct = thongKeService.hoaDonChiTiet(from,to);
        List<Object[]> listHd = thongKeService.soLuongLoaiHoaDon(from,to);
        Map<String, Integer> dataForChart = new HashMap<>();
        Map<String, Integer> dataForChart1 = new HashMap<>();
        List<Object[]> listThongKe = thongKeService.thongKeDoanhThu(from,to);

        Map<LocalDate, BigDecimal> doanhThuMap = new HashMap<>();

        for (Object[] row : listThongKe) {
            BigDecimal totalThanhToan = (BigDecimal) row[0];
            java.sql.Date sqlDate = (java.sql.Date) row[1];
            LocalDate ngayThanhToan = sqlDate.toLocalDate();
            doanhThuMap.put(ngayThanhToan, totalThanhToan);
        }
//        sắp xếp lại thứ tự map theo ngày
        Map<LocalDate, BigDecimal> sortedDoanhThuMap = new LinkedHashMap<>();
        doanhThuMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(entry -> sortedDoanhThuMap.put(entry.getKey(), entry.getValue()));

        for (Object[] result : listHd) {
            String trangThai = String.valueOf(result[0]);
            int soLuong = Integer.parseInt(String.valueOf(result[1]));
            dataForChart.put(trangThai, soLuong);
        }
        for (Object[] result : listHdct) {
            String ten = String.valueOf(result[0]);
            int soLuong = Integer.parseInt(String.valueOf(result[1]));
            dataForChart1.put(ten, soLuong);
        }
        Map<String, Integer> sortedMap = dataForChart1.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        model.addAttribute("listHdct",sortedMap);
        model.addAttribute("listThongKe",sortedDoanhThuMap);
        model.addAttribute("doanhThu", thongKeService.doanhThu(from,to));
        model.addAttribute("table1", thongKeService.getAllSoSanPhamHoanTraHoanLaiKho(from,to));
        model.addAttribute("soDonHuy", thongKeService.soDonHuy( from,  to));
        model.addAttribute("hoanTra", thongKeService.soSanPhamHoanTra( from,  to));
       model.addAttribute("hoadons", hoaDonService.findHoaDonsByNgayTao(from, to));
        model.addAttribute("listHd",dataForChart);
        model.addAttribute("endDate", to);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/thong_ke/thong_ke";
    }

    @GetMapping("/chi-tiet-hoa-don/{id}")
    public String detaiOff(Model model,
                           @PathVariable("id") Long idHd,
                           @Param("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("hoaDon", hoaDonService.findById(idHd));
        model.addAttribute("hdcts", hoaDonChiTietService.getCtspById(idHd));
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/thong_ke/chi_tiet_hoa_don";
    }

    /**
     * get HoaDon Online
     *
     * @param model
     * @param idHd
     * @param trangThai
     * @return
     */
    @GetMapping("/chi-tiet-gio-hang/{id}")
    public String detailOn(Model model,
                           @PathVariable("id") Long idHd,
                           @Param("trangThai") TrangThai trangThai) {
        Long idNhanVien = principalKhachHang.getCurrentNhanVienId();
        if (idNhanVien == null) {
            return "redirect:/login";
        }
        model.addAttribute("hoaDon", hoaDonService.findById(idHd));
        model.addAttribute("ghcts", gioHangChiTietService.findGioHangChiTietById(idHd));
        model.addAttribute("trangThai", trangThai);
        model.addAttribute("tenNhanVien", principalKhachHang.getCurrentNhanVienTen());
        return "admin-template/thong_ke/chi_tiet_hd_online";
    }
    @GetMapping("/export")
    public ResponseEntity<?> exportHD(){
        return importFileExcelHd.createExcel();
    }
    @GetMapping("/export-hoan-tra")
    public ResponseEntity<?> exportSp(){
        return importFileExcelHd.createExcelHoanTra();
    }

}
