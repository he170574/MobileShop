package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
