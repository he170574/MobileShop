package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.service.CategoryService;
import fptu.mobile_shop.MobileShop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ProductControllerView {
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/product-view/productDetail")
    public String showProductDetail(@RequestParam Integer id, Model model) {
        Optional<Product> product = productService.getById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
            return "productDetail"; // Trả về view productDetail.html
        } else {
            return "404"; // Hoặc trang lỗi nếu không tìm thấy sản phẩm
        }
    }
}
