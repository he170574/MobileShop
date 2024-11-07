package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Cart;
import fptu.mobile_shop.MobileShop.entity.CartItem;
import fptu.mobile_shop.MobileShop.entity.Product;
import fptu.mobile_shop.MobileShop.repository.CartItemRepository;
import fptu.mobile_shop.MobileShop.repository.CartRepository;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public int addToCart(Account account, Integer productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepository.getCartByAccountID(account.getAccountId());
        if(Objects.isNull(cart)){
            cart = new Cart();
            cart.setId(0L);
            cart.setAccountID(account.getAccountId());
            cart.setItems(new ArrayList<>());
            cart = cartRepository.save(cart);
        }

        Optional<CartItem> itemCart = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductID().equals(productId))
                .findFirst();

        if (itemCart.isPresent()) {
            itemCart.get().setQuantity(itemCart.get().getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .cart(cart)
                    .build();
            cart.getItems().add(newItem);
            itemCart = cart.getItems().stream()
                    .filter(item -> item.getProduct().getProductID().equals(productId))
                    .findFirst();
        }
        cartItemRepository.save(itemCart.get());
        AtomicInteger totalCart = new AtomicInteger();
        cart.getItems().forEach( cartItem -> {
            totalCart.addAndGet(cartItem.getQuantity());
        });
        return totalCart.get();
    }   

    @Override
    public Cart removeFromCart(Account account, Long cartItemId) {
        Cart cart = cartRepository.findById(1L).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getId().equals(cartItemId));
        cartItemRepository.deleteById(cartItemId);
        return cartRepository.save(cart);
    }

    @Override
    public boolean updateQuantity(Account account, Integer productId, int quantity) {
        try {
            Cart cart = getCart(account);
            if(Objects.isNull(cart) || CollectionUtils.isEmpty(cart.getItems())){
                return false;
            }
            // Xóa sản phẩm khỏi giỏ hàng khi số lượng bằng 0
            CartItem cartItem = cart.getItems().stream()
                    .filter(item -> item.getProduct().getProductID() == productId)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("No CartItem found with the specified product ID"));
            if(Objects.isNull(cartItem)){
                return false;
            }else {
                if(quantity > 0){
                    cartItem.setQuantity(quantity);
                    cartItemRepository.save(cartItem);
                }else {

                    cartItemRepository.deleteById(cartItem.getId());
                    if(cart.getItems().size() < 1){
                        cartRepository.deleteById(cart.getId());
                    };
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Cart getCart(Account account) {

        return cartRepository.getCartByAccountID(account.getAccountId());
    }

    @Override
    public double getCartTotalPrice(Account account) {
        return getCart(account).getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public int getCartTotal(Account account) {
        AtomicInteger totalCart = new AtomicInteger();
        Cart cart = getCart(account);
        if (cart == null) {
            return 0;
        }

        cart.getItems().forEach(cartItem -> {
            totalCart.addAndGet(cartItem.getQuantity());
        });

        return totalCart.get();
        /*getCart(account).getItems().forEach( cartItem -> {
            totalCart.addAndGet(cartItem.getQuantity());
        });
        return totalCart.get();*/
    }
}
