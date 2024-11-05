package fptu.mobile_shop.MobileShop.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/home", "/"})
    public String getHome(Model model) {
        model.addAttribute("homepage", "home");
        return "home";
    }

    @GetMapping("/login")
    public String getLogin() {
        return "redirect:/home";
    }

    @GetMapping("/forgot-pass")
    public String getForgotPassword() {
        return "forgotPass";
    }

    @GetMapping("/edit-info")
    public String getEditAccInfo() {
        return "editAccInfo";
    }

    @GetMapping("/admin/dashboard")
    public String showDashboard() {
        return "statistic";
    }

    @GetMapping("/admin/product")
    public String getAdminProduct() {
        return "manageProduct";
    }

    @GetMapping("/cart")
    public String showCartPage() {
        return "cart";
    }

    @GetMapping("/admin/orders")
    public String showOrder() {
        return "orders";
    }

    @GetMapping("/admin/members")
    public String showMember() {
        return "member";
    }

    @GetMapping("/admin/feedback")
    public String showFeedback() {
        return "feedback-admin";
    }


}
