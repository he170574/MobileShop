package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.UserWithRole;
import fptu.mobile_shop.MobileShop.repository.UserRepository;
import fptu.mobile_shop.MobileShop.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    // Lấy tất cả người dùng kèm vai trò của họ
    public List<UserWithRole> getAllUsersWithRoles() {
        return userRepository.findAllUsersWithRoles();
    }

    // Lấy người dùng theo ID kèm vai trò
    public Optional<UserWithRole> getUserWithRoleById(Integer id) {
        return userRepository.findUserWithRoleById(id);
    }

    // Lưu hoặc cập nhật người dùng kèm vai trò
    public UserWithRole saveUserWithRole(UserWithRole userWithRole) {
        return userRepository.save(userWithRole);
    }

    // Xóa người dùng dựa vào ID
    public void deleteUserWithRoleById(Integer id) {
        userRepository.deleteById(id);
    }
}
