package com.example.befall23datnsd05.export;


import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.HoaDonChiTiet;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class HoaDonPDF {

   public void exportToPDF(
           List<HoaDonChiTiet> list,
           HoaDon hoaDon
   )throws Exception {

//      DiaChi diaChi = new DiaChi();

      // Tạo tài liệu PDF mới
      Document document = new Document();

      // Thiết lập tên file khi xuất ra
      String fileName = hoaDon.getMa() + ".pdf"; // Tên file PDF

      OutputStream outputStream = new FileOutputStream(fileName); // Tạo OutputStream để ghi dữ liệu vào file
      PdfWriter.getInstance(document, outputStream); // Ghi dữ liệu vào tài liệu PDF
      Font font = new Font(BaseFont.createFont("arial/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED));
      font.setColor(0, 0, 0);
      font.setStyle(Font.BOLD);
      document.setPageSize(PageSize.A4);
      document.open();

// Các dòng mã khác để tạo nội dung PDF

// Cuối cùng, đóng OutputStream và tài liệu PDF
//      outputStream.close();
//      document.close();

      // Tạo đối tượng PdfWriter để ghi dữ liệu vào tài liệu PDF
//      PdfWriter.getInstance(document, response.getOutputStream());

//
//      // Mở tài liệu PDF để bắt đầu viết
//      document.open();

      // Thiết kế tài liệu PDF giống như đoạn mã HTML
      Paragraph header1 = new Paragraph("Hóa Đơn Bán Hàng",new Font(font));
      header1.setAlignment(Element.ALIGN_CENTER);
      header1.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 16, Font.BOLD));
      String diaChi2 = "";
      if(hoaDon.getDiaChi() != null){
         diaChi2 = hoaDon.getDiaChi();
      }

      Paragraph paragraph1 = new Paragraph("GIÀY THỂ THAO WINGMAN", new Font(font));
      paragraph1.setIndentationLeft(50);
      paragraph1.setIndentationRight(60);
      paragraph1.setAlignment(Element.ALIGN_CENTER);
      paragraph1.setSpacingAfter(0);

      Paragraph paragraph2 = new Paragraph("        ________________________________________", new Font(font));
      paragraph2.setSpacingBefore(5);
      paragraph2.setAlignment(Element.ALIGN_CENTER);
      paragraph2.setIndentationLeft(50);
      paragraph2.setIndentationRight(60);

      // Tạo một bảng với hai cột
      PdfPTable table1 = new PdfPTable(2);
      table1.setPaddingTop(20);

// Thêm cột thứ nhất chứa thông tin khách hàng và mã hóa đơn
      PdfPCell cot1 = new PdfPCell();
      cot1.setBorder(Rectangle.NO_BORDER);
      cot1.addElement(new Paragraph("Khách hàng: " + hoaDon.getTenKhachHang(), new Font(font)));
      cot1.addElement(new Paragraph("Mã Hóa Đơn: " + hoaDon.getMa(), new Font(font)));
      table1.addCell(cot1);

// Thêm cột thứ hai chứa thông tin nhân viên và ngày
      PdfPCell cot2 = new PdfPCell();
      cot2.setBorder(Rectangle.NO_BORDER);

      cot2.addElement(new Paragraph("Nhân viên: " + hoaDon.getNhanVien().getTen(), new Font(font)));
      cot2.addElement(new Paragraph("Ngày: " + hoaDon.getNgayThanhToan(), new Font(font)));
      table1.addCell(cot2);

// Thêm bảng vào tài liệu PDF
//      document.add(table1);

//      Paragraph paragraph5 = new Paragraph("Khách hàng: " +hoaDon.getTenKhachHang() , new Font(font));
//      paragraph5.setIndentationLeft(50);
//      paragraph5.setIndentationRight(20);
//      paragraph5.setAlignment(Element.ALIGN_LEFT);
//      paragraph5.setSpacingAfter(0);
//
//      Paragraph paragraph6 = new Paragraph("Mã Hóa Đơn: "+hoaDon.getMa(),new Font(font));
//      paragraph6.setIndentationLeft(50);
//      paragraph6.setIndentationRight(20);
//      paragraph6.setAlignment(Element.ALIGN_LEFT);
//      paragraph6.setSpacingAfter(0);
//
//      Paragraph paragraph7 = new Paragraph("Nhân viên: " +hoaDon.getNhanVien().getTen() , new Font(font));
//      paragraph7.setIndentationLeft(50);
//      paragraph7.setIndentationRight(50);
//      paragraph7.setAlignment(Element.ALIGN_RIGHT);
//      paragraph7.setSpacingAfter(0);
//
//      Paragraph paragraph8 = new Paragraph("Ngày: " +hoaDon.getNgayThanhToan() , new Font(font));
//      paragraph8.setIndentationLeft(50);
//      paragraph8.setIndentationRight(50);
//      paragraph8.setAlignment(Element.ALIGN_RIGHT);
//      paragraph8.setSpacingAfter(0);

      Paragraph header2 = new Paragraph(diaChi2,new Font(font));
      header2.setAlignment(Element.ALIGN_CENTER);
      header2.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC, 12));

//      Paragraph header3 = new Paragraph("Hóa Đơn Bán Hàng",new Font(font));
//      header3.setAlignment(Element.ALIGN_CENTER);
//      paragraph5.setSpacingBefore(10);
//      header3.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD));

      float columnWidth[] = {25,15,10,10,20,20};

      PdfPTable table = new PdfPTable(columnWidth);
      table.setSpacingBefore(25);
      table.setSpacingAfter(25);

      PdfPCell cell1 = new PdfPCell(new Phrase("Tên sản phẩm", new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

      PdfPCell cell2 = new PdfPCell(new Phrase("Màu sắc", new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

      PdfPCell cell3 = new PdfPCell(new Phrase("Kích thước", new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell1.setHorizontalAlignment(Element.ALIGN_CENTER);

      PdfPCell cell4 = new PdfPCell(new Phrase("Số lượng",new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

      PdfPCell cell5 = new PdfPCell(new Phrase("Đơn giá", new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell3.setHorizontalAlignment(Element.ALIGN_CENTER);

      PdfPCell cell6 = new PdfPCell(new Phrase("Thành tiền",new Font(font)));
//      paragraph5.setSpacingBefore(10);
      cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

      table.addCell(cell1);
      table.addCell(cell2);
      table.addCell(cell3);
      table.addCell(cell4);
      table.addCell(cell5);
      table.addCell(cell6);

      // Lấy danh sách hóa đơn chi tiết từ model
      //List<HoaDonChiTiet> lstHDCT = (List<HoaDonChiTiet>) model.getAttribute("lstHDCT");
      NumberFormat currencyFormat02 = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("vi-VN"));
      currencyFormat02.setMinimumFractionDigits(0);
      // Thêm dữ liệu vào bảng
      for (HoaDonChiTiet hdct : list) {
         PdfPCell cell7 = new PdfPCell(new Phrase(hdct.getChiTietSanPham().getSanPham().getTen(), new Font(font)));
         cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
         PdfPCell cell8 = new PdfPCell(new Phrase(hdct.getChiTietSanPham().getMauSac().getTen(), new Font(font)));
         cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
         PdfPCell cell9 = new PdfPCell(new Phrase( hdct.getChiTietSanPham().getKichThuoc().getTen(), new Font(font)));
         cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
         PdfPCell cell10 = new PdfPCell(new Phrase("" + hdct.getSoLuong(), new Font(font)));
         cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
         cell6.setMinimumHeight(10);
         PdfPCell cell11 = new PdfPCell(new Phrase( currencyFormat02.format(hdct.getGiaBan().doubleValue() ), new Font(font)));
         cell7.setHorizontalAlignment(Element.ALIGN_CENTER);
         PdfPCell cell12 = new PdfPCell(new Phrase( currencyFormat02.format(hdct.getGiaBan().multiply(BigDecimal.valueOf(hdct.getSoLuong())).doubleValue()), new Font(font)));
         cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(cell7);
         table.addCell(cell8);
         table.addCell(cell9);
         table.addCell(cell10);
         table.addCell(cell11);
         table.addCell(cell12);
      }

      // Tổng tiền
      Paragraph tongTien = new Paragraph("Tổng tiền: " + currencyFormat02.format(hoaDon.getTongTien().doubleValue()), new Font(font));
      tongTien.setAlignment(Element.ALIGN_RIGHT);
      tongTien.setIndentationLeft(50);
      tongTien.setIndentationRight(50);
      tongTien.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

      // Xu
      BigDecimal xuTichDiem = new BigDecimal(0);
      if (hoaDon.getXu() != null){
         xuTichDiem = hoaDon.getXu();
      }
      Paragraph xu = new Paragraph("Xu: " + currencyFormat02.format(xuTichDiem.doubleValue()), new Font(font));
      xu.setAlignment(Element.ALIGN_RIGHT);
      xu.setIndentationLeft(50);
      xu.setIndentationRight(50);
      xu.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

      // Thành tiền
      Paragraph thanhTien = new Paragraph("Thành tiền: " + currencyFormat02.format(hoaDon.getThanhToan().doubleValue()), new Font(font));
      thanhTien.setAlignment(Element.ALIGN_RIGHT);
      thanhTien.setIndentationLeft(50);
      thanhTien.setIndentationRight(50);
      thanhTien.setFont(FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Font.BOLD));

      // Thêm các phần tử vào tài liệu PDF
      document.add(paragraph1);
      document.add(paragraph2);
//      document.add(paragraph3);
      document.add(header1);
      document.add(header2);
//      document.add(header3);
//      document.add(paragraph5);
//      document.add(paragraph6);
//      document.add(paragraph7);
//      document.add(paragraph8);
      document.add(table1);
      document.add(table);
      document.add(tongTien);
      document.add(xu);
      document.add(thanhTien);
//      document.add(paragraph8);
//      document.add(paragraph9);
//      document.add(paragraph10);
//      document.add(paragraph11);

      // Đóng tài liệu PDF
      document.close();

   }

}
