package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
