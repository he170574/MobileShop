package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.entity.CartItem;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.CartItemRepository;
import fptu.mobile_shop.MobileShop.repository.CartRepository;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Cart addToCart(Integer productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.findById(1L) // Assume default cart ID, can change as per logic
                .orElse(new Cart());

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductID().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeFromCart(Long cartItemId) {
        Cart cart = cartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartItemRepository.deleteById(cartItemId);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(Long cartItemId, int quantity) {
        Cart cart = cartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .forEach(item -> item.setQuantity(quantity));
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCart() {
        return cartRepository.findById(1L).orElse(new Cart());
    }

    @Override
    public double getCartTotalPrice() {
        return getCart().getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }
}
