package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    // Bạn có thể thêm các phương thức tùy chỉnh nếu cần
}
