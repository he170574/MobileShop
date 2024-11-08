package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart getCartByAccountID(int accountID);

    @Query(value =  " SELECT c FROM Cart c " +
            " WHERE 1 = 1 " +
            " AND c.accountID = :accountId ")
    Optional<Cart> findByAccountId(Integer accountId);
}
