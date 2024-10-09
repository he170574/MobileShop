package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.UserWithRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserWithRole, Integer> {

    // Custom query để lấy tất cả người dùng và vai trò của họ
    @Query("SELECT u FROM UserWithRole u JOIN FETCH u.userroles")
    List<UserWithRole> findAllUsersWithRoles();

    // Custom query để lấy người dùng dựa vào ID kèm vai trò
    @Query("SELECT u FROM UserWithRole u JOIN FETCH u.userroles WHERE u.id = :id")
    Optional<UserWithRole> findUserWithRoleById(Integer id);
}
