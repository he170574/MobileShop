package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.Cart;

public interface CartService {

    Cart addToCart(Integer productId, int quantity);

    Cart removeFromCart(Long cartItemId);

    Cart updateQuantity(Long cartItemId, int quantity);

    Cart getCart();

    double getCartTotalPrice();
}
