package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.entity.User1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface User1Repository extends JpaRepository<User1, Long> {
    User findByEmail(String email);
}
