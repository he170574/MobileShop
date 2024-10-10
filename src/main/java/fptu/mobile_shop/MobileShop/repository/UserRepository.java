package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Tìm người dùng theo email
    Optional<User> findByEmail(String email);


}
