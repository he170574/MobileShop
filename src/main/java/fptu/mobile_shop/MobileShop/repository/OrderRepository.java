package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  OrderRepository extends JpaRepository<Order, Long> {
}
