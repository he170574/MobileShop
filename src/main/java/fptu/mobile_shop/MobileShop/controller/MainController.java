package fptu.mobile_shop.MobileShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/admin/product")
    public String getAdminProduct() {
        return "AdminProduct"; // Trả về trang products.html
    }

    @GetMapping("/admin/categories")
    public String getCategories() {
        return "categories"; // Trả về trang categories.html
    }

    @GetMapping("/admin/customers")
    public String getCustomers() {
        return "customers"; // Trả về trang customers.html
    }

    @GetMapping("/admin/dashboard")
    public String getDashboard() {
        return "dashboard"; // Trả về trang dashboard.html
    }

    @GetMapping("/admin/index")
    public String getIndex() {
        return "index"; // Trả về trang index.html
    }

    @GetMapping("/admin/orders")
    public String getOrders() {
        return "orders"; // Trả về trang orders.html
    }

    @GetMapping("/admin/reports")
    public String getReports() {
        return "reports"; // Trả về trang reports.html
    }

}
