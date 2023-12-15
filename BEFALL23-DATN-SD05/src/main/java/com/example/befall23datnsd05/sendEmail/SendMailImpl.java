package com.example.befall23datnsd05.sendEmail;

import com.example.befall23datnsd05.entity.GioHangChiTiet;
import com.example.befall23datnsd05.entity.HoaDon;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.enumeration.TrangThaiDonHang;
import com.example.befall23datnsd05.repository.GioHangChiTietRepository;
import com.example.befall23datnsd05.repository.KhachHangRepository;
import com.example.befall23datnsd05.worker.CodeGenerator;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class SendMailImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;

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
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        DecimalFormat decimalFormat = (DecimalFormat) currencyFormat;
        decimalFormat.applyPattern("#,### VND");
        String from = "wingmansd05@gmail.com";
        String to = khachHang.getEmail();
        String subject = "Thông tin hóa đơn";
        StringBuilder content = new StringBuilder("<div style=\"font-family: 'Arial', sans-serif; font-size: 16px; color: black;\">")
                .append("<p style=\"color: black;\"><b>Xin chào ").append(khachHang.getTen()).append(",</b></p>")
                .append("<p style=\"color: black;\">Cảm ơn bạn đã tin tưởng và mua hàng tại Wingman.</p>");

        if (hoaDon.getTrangThai() == TrangThaiDonHang.DANG_CHUAN_BI) {
            content.append("<p style=\"color: black;\">Chúng tôi xin thông báo rằng đơn hàng của bạn hiện đang trong quá trình chuẩn bị và sẽ sớm được gửi đến địa chỉ của bạn.</p>")
                    .append("<p style=\"color: black;\">Quý khách có thể tra cứu thông tin đơn hàng tại đây: http://localhost:8080/wingman/tra-cuu-don-hang</p>")
                    .append("<p style=\"color: black;\">Xin cảm ơn vì đã lựa chọn sản phẩm của chúng tôi!</p>");
        } else if (hoaDon.getTrangThai() == TrangThaiDonHang.DA_GIAO) {
            content.append("<p style=\"color: black;\">Chúc mừng! Đơn hàng của bạn đã được giao thành công đến địa chỉ của bạn!</p>")
                    .append("<p style=\"color: black;\">Chúng tôi rất vui mừng vì đã có cơ hội phục vụ bạn và hy vọng bạn sẽ hài lòng với sản phẩm đã mua.</p>")
                    .append("<p style=\"color: black;\">Vui lòng đăng nhập để xác nhận bạn đã nhận hàng và hài lòng với sản phẩm trong vòng 3 ngày</p>")
                    .append("<p style=\"color: black;\">Quý khách có thể tra cứu thông tin đơn hàng tại đây: http://localhost:8080/wingman/tra-cuu-don-hang</p>")
                    .append("<p style=\"color: black;\">Nếu có bất kỳ thắc mắc hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi ngay!</p>")
                    .append("<p style=\"color: black;\">Xin cảm ơn bạn đã lựa chọn sản phẩm của chúng tôi!</p>");
        } else if (hoaDon.getTrangThai() == TrangThaiDonHang.DA_HUY) {
            content.append("<p style=\"color: black;\">Xin chào!</p>")
                    .append("<p style=\"color: black;\">Chúng tôi rất tiếc phải thông báo rằng đơn hàng của bạn đã bị huỷ.</p>")
                    .append("<p style=\"color: black;\">Chúng tôi xin lỗi về bất kỳ bất tiện nào gây ra và hy vọng có cơ hội phục vụ bạn trong tương lai.</p>")
                    .append("<p style=\"color: black;\">Nếu có bất kỳ thắc mắc hoặc cần hỗ trợ, vui lòng liên hệ với chúng tôi ngay!</p>")
                    .append("<p style=\"color: black;\">Xin cảm ơn bạn đã quan tâm và lựa chọn sản phẩm của chúng tôi!</p>");
        } else {
            content.append("<p style=\"color: black;\">Đơn hàng của bạn hiện đang được xử lý. Chúng tôi sẽ thông báo khi đơn hàng gửi đến cho bạn.</p>")
                    .append("<p style=\"color: black;\">Chúng tôi rất cảm kích sự kiên nhẫn của bạn trong quá trình này.</p>")
                    .append("<p style=\"color: black;\">Quý khách có thể tra cứu thông tin đơn hàng tại đây: http://localhost:8080/wingman/tra-cuu-don-hang</p>")
                    .append("<p style=\"color: black;\">Xin cảm ơn bạn đã lựa chọn sản phẩm của chúng tôi!</p>");
        }
        if (hoaDon.getTrangThai() != TrangThaiDonHang.DA_HUY) {
            content.append("<p style=\"color: black;\"><strong>Thông tin đơn hàng: [[maHoaDon]]</strong></p>");
            content.append("<table border=\"1\" style=\"border-collapse: collapse;\">")
                    .append("<tr>")
                    .append("<th style=\"border: 1px solid black;\">STT</th>")
                    .append("<th style=\"border: 1px solid black;\">Tên sản phẩm</th>")
                    .append("<th style=\"border: 1px solid black;\">Cổ giày</th>")
                    .append("<th style=\"border: 1px solid black;\">Lót Giày</th>")
                    .append("<th style=\"border: 1px solid black;\">Kích Thước</th>")
                    .append("<th style=\"border: 1px solid black;\">Đế giày</th>")
                    .append("<th style=\"border: 1px solid black;\">Dòng sản phẩm</th>")
                    .append("<th style=\"border: 1px solid black;\">Thương Hiệu</th>")
                    .append("<th style=\"border: 1px solid black;\">Đơn giá</th>")
                    .append("<th style=\"border: 1px solid black;\">Số lượng</th>")
                    .append("</tr>");
            int stt = 1;
            List<GioHangChiTiet> gioHangChiTiets = gioHangChiTietRepository.findByHoaDon(hoaDon.getId());
            for (GioHangChiTiet gioHangChiTiet : gioHangChiTiets) {
                content.append("<tr>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(stt++).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getSanPham().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getCoGiay().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getLotGiay().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getKichThuoc().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getDeGiay().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getSanPham().getDongSanPham().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getChiTietSanPham().getSanPham().getThuongHieu().getTen()).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(decimalFormat.format(gioHangChiTiet.getDonGia())).append("</td>")
                        .append("<td style=\"border: 1px solid black;color: black;\">").append(gioHangChiTiet.getSoLuong()).append("</td>")
                        .append("</tr>");
            }
            content.append("</table>");


            if (hoaDon.getMaGiamGia() != null) {
                content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                        "Mã Giảm Giá: [[maGiamGia]]\n" +
                        "</p>\n"
                );
            }
            content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                    "Phí vận chuyển:[[phiVanChuyen]]\n" +
                    "</p>\n" +
                    "<p style=\"color: black;\" class=\"email-content\">\n" +
                    "Tổng tiền:[[tongTien]]\n" +
                    "</p>\n" +
                    "<p style=\"color: black;\" class=\"email-content\">\n" +
                    "Thanh toán: [[thanhToan]]\n" +
                    "</p>\n"
            );
            if (hoaDon.getNgayThanhToan() != null) {
                content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                        "Ngày thanh toán: [[ngayThanhToan]]\n" +
                        "</p>\n"
                );
            }
            content.append("<br>\n" +
                    "<p style=\"color: black;\" class=\"email-content\">\n" +
                    "Cảm ơn bạn đã chọn Wingman! Nếu có bất kỳ thắc mắc nào hoặc cần hỗ trợ, hãy liên hệ với chúng tôi qua số :0329962003.\n" +
                    "</p>" +
                    "</div>"
            );
        }

        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String ngayThanhToanFormatted;
            if (hoaDon.getNgayThanhToan() != null) {
                ngayThanhToanFormatted = hoaDon.getNgayThanhToan().format(formatter);
            } else {
                ngayThanhToanFormatted = "";
            }

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
    public boolean sendNewPassWord(String verificationPassWord, String resetPass) {
        return false;
    }

    @Override
    public void sendNewPassWord(String mail) {
        KhachHang khachHang = khachHangRepository.findByEmail(mail).get();
        String from = "wingmansd05@gmail.com";
        String to = mail;
        String subject = "Quên mật khẩu";
        StringBuilder content = new StringBuilder("<div style=\"font-family: 'Arial', sans-serif; font-size: 16px; color: black;\">")
                .append("<p style=\"color: black;\"><b>Xin chào ").append(khachHang.getTen()).append(",</b></p>")
                .append("<p style=\"color: black;\">Bạn đã xác nhận quên mật khẩu và yêu cầu tìm lại mật khẩu của mình vào : [[time]]</p>");

        content.append("<p style=\"color: black;\">Chúng tôi đã nhận được yêu cầu khôi phục mật khẩu từ phía bạn. Dưới đây là mật khẩu mới để truy cập vào tài khoản của bạn: [[newPass]].</p>");

        content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                "Đề nghị bạn đăng nhập bằng mật khẩu này và sau đó thay đổi mật khẩu trong phần cài đặt tài khoản của mình để đảm bảo tính bảo mật cho tài khoản của bạn.\n" +
                "</p>\n");
        content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                "Nếu có bất kỳ câu hỏi hoặc vấn đề gì, đừng ngần ngại liên hệ với chúng tôi. Chúng tôi luôn ở đây để hỗ trợ bạn.\n" +
                "</p>\n");
        content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                "Trân trọng,\n" +
                "</p>\n");
        content.append("<p style=\"color: black;\" class=\"email-content\">\n" +
                "Wingman\n" +
                "</p>\n");
        try {

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String ngayGioFormatted = formatter.format(now);

            helper.setFrom(from, "Wingman");
            helper.setTo(to);
            helper.setSubject(subject);
            String content1 = content.toString();
            content1 = content1.replace("[[name]]", khachHang.getTen());
            content1 = content1.replace("[[newPass]]", CodeGenerator.genUUID());
            content1 = content1.replace("[[time]]", ngayGioFormatted);
            helper.setText(content1, true);
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
