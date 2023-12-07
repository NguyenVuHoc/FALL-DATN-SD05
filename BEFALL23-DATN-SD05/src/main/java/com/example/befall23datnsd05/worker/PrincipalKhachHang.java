package com.example.befall23datnsd05.worker;

import com.example.befall23datnsd05.security.customer.CustomKhachHangDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalKhachHang {
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy thông tin chi tiết của người dùng
            Object principal = authentication.getPrincipal();

            // Kiểm tra xem principal có phải là một đối tượng UserDetails không
            if (principal instanceof CustomKhachHangDetail) {
                // Lấy ID của người dùng từ UserDetails
                return ((CustomKhachHangDetail) principal).getKhachHangId();
            }
        }

        return null; // Hoặc có thể trả về giá trị mặc định hoặc ném một ngoại lệ tùy vào logic của bạn
    }
}
