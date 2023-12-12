package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class SendMailImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(KhachHang khachHang, String url) {
        String from = "wingmansd05@gmail.com";
        String to = khachHang.getEmail();
        String subject = "Khôi Phục Mật Khẩu Tài Khoản WingMan của Bạn";
        String content = "<p class=\"email-content\" style=\"font-family: 'Arial', sans-serif;font-size: 16px;color: #333;line-height: 1.5;\">\n" +
                "Chào [[name]], <br>\n" +
                "Chúc mừng! Bạn đã yêu cầu hướng dẫn khôi phục mật khẩu cho tài khoản của mình trên Glacat. Để tiếp tục quá trình này, vui lòng nhấn vào liên kết dưới đây:\n" +
                "</p>\n" +

                "<p class=\"email-content\">\n" +
                "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" +
                "</p>\n" +

                "<p class=\"email-content\">\n" +
                "Nếu bạn không yêu cầu hướng dẫn khôi phục mật khẩu hoặc không nhớ việc này, hãy bỏ qua email này. Liên kết xác nhận sẽ hết hạn sau 24 giờ.\n" +
                "<br>\n" +
                "Chân thành cảm ơn,\n" +
                "<br>\n" +
                "Đội ngũ Wingman\n" +
                "</p>";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Wingman");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", khachHang.getEmail());
            String siteUrl = url + "/verify?code=" + khachHang.getMatKhau();

            System.out.println(siteUrl);

            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendEmail1(KhachHang khachHang, HoaDon hoaDon) {
        String from = "wingmansd05@gmail.com";
        String to = khachHang.getEmail();
        String subject = "Thông tin hóa đơn";
        StringBuilder content = new StringBuilder(
                "<p class=\"email-content\" style=\"font-family: 'Arial', sans-serif;font-size: 16px;color: #333;line-height: 1.5;\">\n" +
                        "Xin chào [[name]], <br>\n" +
                        "Cảm ơn bạn đã tin tưởng và mua hàng tại Wingman. Đơn hàng của bạn đang được xử lý và sẽ đến tay bạn sớm\n" +
                        "</p>\n" +

                        "<p class=\"email-content\">\n" +
                        "**Thông tin đơn hàng**\n" +
                        "</p>\n" +
                        "<p class=\"email-content\">\n" +
                        "Mã hóa đơn: [[maHoaDon]]\n" +
                        "</p>\n" +
                        "<p class=\"email-content\">\n" +
                        "Địa chỉ: [[diaChi]]\n" +
                        "</p>\n" +
                        "<p class=\"email-content\">\n" +
                        "Số điện thoại: [[sdt]]\n" +
                        "</p>\n"
        );
        if (hoaDon.getMaGiamGia() != null) {
            content.append("<p class=\"email-content\">\n" +
                    "Ma gaim: [[maGiamGia]]\n" +
                    "</p>\n"
            );
        }
        content.append("<p class=\"email-content\">\n" +
                "Phí vận chuyển:[[phiVanChuyen]]\n" +
                "</p>\n" +
                "<p class=\"email-content\">\n" +
                "Tổng tiền:[[tongTien]]\n" +
                "</p>\n" +
                "<p class=\"email-content\">\n" +
                "Thanh toán: [[thanhToan]]\n" +
                "</p>\n"
        );
        if (hoaDon.getNgayThanhToan() != null) {
            content.append("<p class=\"email-content\">\n" +
                    "Ma gaim: [[ngayThanhToan]]\n" +
                    "</p>\n"
            );
        }
        content.append("<br>\n" +
                "<p class=\"email-content\">\n" +
                "Cảm ơn bạn đã chọn Wingman! Nếu có bất kỳ thắc mắc nào hoặc cần hỗ trợ, hãy liên hệ với chúng tôi.\n" +
                "</p>"
        );
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            DecimalFormat decimalFormat = (DecimalFormat) currencyFormat;
            decimalFormat.applyPattern("#,### VND");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayThanhToanFormatted = hoaDon.getNgayThanhToan().format(formatter);

            helper.setFrom(from, "Wingman");
            helper.setTo(to);
            helper.setSubject(subject);

            String content1 = content.toString();

            content1 = content1.replace("[[name]]", khachHang.getTen());
            content1 = content1.replace("[[maHoaDon]]", hoaDon.getMa());
            content1 = content1.replace("[[diaChi]]", hoaDon.getDiaChi());
            content1 = content1.replace("[[sdt]]", hoaDon.getSdt());
            if (hoaDon.getMaGiamGia() != null) {
                content1 = content1.replace("[[maGiamGia]]", String.valueOf(hoaDon.getMaGiamGia().getMa()));
            }
            content1 = content1.replace("[[phiVanChuyen]]", decimalFormat.format(hoaDon.getPhiVanChuyen()));
            content1 = content1.replace("[[thanhToan]]", decimalFormat.format(hoaDon.getThanhToan()));
            content1 = content1.replace("[[tongTien]]", decimalFormat.format(hoaDon.getTongTien()));
            if (hoaDon.getNgayThanhToan() != null) {
                content1 = content1.replace("[[ngayThanhToan]]", ngayThanhToanFormatted);
            }
            helper.setText(content1, true);

            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean verifyAccount(String verificationPassWord, String resetPass) {
        return false;
    }
}
