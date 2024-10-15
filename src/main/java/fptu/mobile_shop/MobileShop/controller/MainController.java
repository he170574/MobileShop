package fptu.mobile_shop.MobileShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/admin/product")
    public String getAdminProduct() {
        return "manageProduct";
    }

    @GetMapping("/cart")
    public String showCartPage() { return "cart";
    }

    @GetMapping("/home")
    public String showHomePage() { return "index";

    }

}
