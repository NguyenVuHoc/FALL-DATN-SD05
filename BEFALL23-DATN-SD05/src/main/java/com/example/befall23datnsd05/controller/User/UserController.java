package com.example.befall23datnsd05.controller.User;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.DiaChi;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.service.DiaChiService;
import com.example.befall23datnsd05.service.KhachHangService;
import com.example.befall23datnsd05.worker.PrincipalKhachHang;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wingman")
public class UserController {
    private final KhachHangService khachHangService;
    private final DiaChiService diaChiService;
    private PrincipalKhachHang principalKhachHang = new PrincipalKhachHang();

    public UserController(KhachHangService khachHangService, DiaChiService diaChiService) {
        this.khachHangService = khachHangService;
        this.diaChiService = diaChiService;
    }

    /**
     * Get User By IdKh
     *
     * @param model
     * @return
     */
    @GetMapping("/thong-tin-cua-toi")
    public String getAll(Model model) {
        Long id = principalKhachHang.getCurrentUserId();
        if (id == null) {
            return "redirect:/login";
        }
        KhachHang khachHang = khachHangService.getById(id);
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
        model.addAttribute("diaChi", new DiaChiRequest());
        return "customer-template/user/profile";
    }

    /**
     * Update KhachHang
     *
     * @param khachHangRequest
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("khachHang") KhachHangRequest khachHangRequest,
                         BindingResult bindingResult,
                         Model model
    ) {
        Long id = khachHangRequest.getId();
        String sdt = khachHangRequest.getSdt();
        KhachHang khachHang = khachHangService.getById(id);
        if (khachHangService.existsBySdtAndIdNot(sdt, id)) {
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("errorTen", "Số điện thoại đã tồn tại");
            return "customer-template/user/profile";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Số điện thoại hoặc tên không được để trống");
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
            model.addAttribute("diaChi", new DiaChiRequest());
            return "customer-template/user/profile";
        }
        model.addAttribute("success", "Cập nhật thành công!");
        khachHangService.update(khachHangRequest);
        return "redirect:/wingman/thong-tin-cua-toi?success";
    }

    /**
     * Âdd new address
     *
     * @param diaChiRequest
     * @param idKhachHang
     * @return
     */
    @PostMapping("/add-dia-chi/{idKhachHang}")
    public String addDiaChi(
            @Valid
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,
            @PathVariable("idKhachHang") String idKhachHang,
            @RequestParam("phuongXaID") String phuongXa,
            @RequestParam("quanHuyenID") String quanHuyen,
            @RequestParam("thanhPhoID") String thanhPho
    ) {
        diaChiService.add(diaChiRequest, Long.valueOf(idKhachHang), thanhPho, quanHuyen, phuongXa);
        return "redirect:/wingman/thong-tin-cua-toi?success";
    }

    /**
     * Update adress
     *
     * @param id
     * @param idKH
     * @param diaChiRequest
     * @param model
     * @return
     */
    @PostMapping("/update-dia-chi/{id}/{idKH}")
    public String updateDiaChi(
            @PathVariable("id") Long id,
            @PathVariable("idKH") Long idKH,
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,
            @RequestParam("phuongXa") String phuongXa,
            @RequestParam("quanHuyen") String quanHuyen,
            @RequestParam("thanhPho") String thanhPho,
            Model model
    ) {
        KhachHang khachHang = khachHangService.getById(idKH);
        if (diaChiRequest.getTenNguoiNhan().isEmpty()) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(idKH));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Tên người nhận không được để trống!");
            return "customer-template/user/profile";
        }
        if (diaChiRequest.getSdt().isEmpty()) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(idKH));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Số điện thoại không được để trống!");
            return "customer-template/user/profile";
        }
        if (diaChiRequest.getDiaChi().isEmpty()) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(idKH));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Địa chỉ không được để trống!");
            return "customer-template/user/profile";
        } else {
            diaChiService.update(diaChiRequest, thanhPho, quanHuyen, phuongXa);
        }
        return "redirect:/wingman/thong-tin-cua-toi?success";
    }

    /**
     * Delete address By id
     *
     * @param id
     * @param idKH
     * @return
     */
    @GetMapping("/delete-dia-chi/{id}/{idKH}")
    public String deleteDiaChi(@PathVariable("id") Long id,
                               @PathVariable("idKH") Long idKH

    ) {
        diaChiService.remove(id);
        return "redirect:/wingman/thong-tin-cua-toi?success";
    }

    /**
     * Change passwword
     *
     * @param idKh
     * @param oldPassword
     * @param newPassword
     * @param model
     * @return
     */
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("id") Long idKh,
                                 @RequestParam("oldPassword") String oldPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 Model model) {
        KhachHang khachHang = khachHangService.getById(idKh);
        if (khachHang.getMatKhau().isEmpty()) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(idKh));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Mật khẩu không được để trống!");
            return "customer-template/user/profile";
        }
        if (!khachHang.getMatKhau().equals(oldPassword)) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(idKh));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("khachHang", khachHang);
            model.addAttribute("errorTen", "Mật khẩu bạn nhập không trùng khớp!");
            return "customer-template/user/profile";
        } else {

            khachHangService.changeUserPassword(idKh, oldPassword, newPassword);
            return "redirect:/wingman/thong-tin-cua-toi?success";
        }
    }
}
