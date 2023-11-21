package com.example.befall23datnsd05.controller.User;

import com.example.befall23datnsd05.dto.DiaChiRequest;
import com.example.befall23datnsd05.dto.KhachHangRequest;
import com.example.befall23datnsd05.entity.KhachHang;
import com.example.befall23datnsd05.service.DiaChiService;
import com.example.befall23datnsd05.service.KhachHangService;
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

    public UserController(KhachHangService khachHangService, DiaChiService diaChiService) {
        this.khachHangService = khachHangService;
        this.diaChiService = diaChiService;
    }

    /**
     * Get User By IdKh
     * @param id
     * @param model
     * @param diaChiRequest
     * @return
     */
    @GetMapping("/thong-tin-cua-toi/{id}")
    public String viewUpdate(@PathVariable("id") Long id, Model model,@ModelAttribute("diaChi") DiaChiRequest diaChiRequest ) {
        KhachHang khachHang = khachHangService.getById(id);
        model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
        model.addAttribute("khachHang", khachHang);
        model.addAttribute("diaChi", diaChiRequest);
        return "customer-template/user/profile";
    }

    /**
     * Update KhachHang
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
        if (khachHangService.existsBySdtAndIdNot(sdt, id)) {
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
            model.addAttribute("diaChi", new DiaChiRequest());
            model.addAttribute("errorTen", "Số điện thoại đã tồn tại");
            return "customer-template/user/profile";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorTen", "Số điện thoại hoặc tên không được để trống");
            model.addAttribute("listDC", diaChiService.getAllTheoKhachHang(id));
            model.addAttribute("diaChi", new DiaChiRequest());
            return "customer-template/user/profile";
        }
        model.addAttribute("success", "Sửa thành công");
        khachHangService.update(khachHangRequest);
        return "redirect:/wingman/thong-tin-cua-toi/" + id + "?success";
    }

    /**
     * Âdd new address
     * @param diaChiRequest
     * @param idKhachHang
     * @return
     */
    @PostMapping("/add-dia-chi/{idKhachHang}")
    public String addDiaChi(
            @Valid
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,
            @PathVariable("idKhachHang") String idKhachHang
    ) {
        diaChiService.add(diaChiRequest, Long.valueOf(idKhachHang));
        return "redirect:/wingman/thong-tin-cua-toi/" + idKhachHang + "?success";
    }

    /**
     * Update adress
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
            @ModelAttribute("diaChi") DiaChiRequest diaChiRequest,Model model
    ) {

        diaChiService.update(diaChiRequest, id);
        return "redirect:/wingman/thong-tin-cua-toi/" + idKH + "?success";
    }

    /**
     * Delete address By id
     * @param id
     * @param idKH
     * @return
     */
    @GetMapping("/delete-dia-chi/{id}/{idKH}")
    public String deleteDiaChi(@PathVariable("id") Long id,
                               @PathVariable("idKH") Long idKH

    ) {
        diaChiService.remove(id);
        return "redirect:/wingman/thong-tin-cua-toi/" + idKH + "?success";
    }


}
