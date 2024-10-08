package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);  // Tìm người dùng theo email
}
