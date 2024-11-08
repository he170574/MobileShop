package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Cart;

public interface CartService {

    int addToCart(Account account,Integer productId, int quantity);

    Cart removeFromCart(Account account, Long cartItemId);

    boolean updateQuantity(Account account, Integer cartItemId, int quantity);

    Cart getCart(Account account);

    double getCartTotalPrice(Account account);
    int getCartTotal(Account account);

    Cart findByAccountId();

    double calculateTotalAmount(Cart cart);

    void deleteCart(Cart cart);
}
