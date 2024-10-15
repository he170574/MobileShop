package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.CartItemDTO;
import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add-to-cart")
    public Cart addToCart(@RequestBody CartItemDTO cartItemDTO) {
        return cartService.addToCart(cartItemDTO.getProductId(), cartItemDTO.getQuantity());
    }

    @PostMapping("/remove-cart")
    public Cart removeFromCart(@RequestParam Long cartItemId) {
        return cartService.removeFromCart(cartItemId);
    }

    @GetMapping("/view-cart")
    public Cart viewCart() {
        return cartService.getCart();
    }

    @PostMapping("/update-quantity")
    public Cart updateQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
        return cartService.updateQuantity(cartItemId, quantity);
    }

    @GetMapping("/total")
    public double getTotalPrice() {
        return cartService.getCartTotalPrice();
    }
}

