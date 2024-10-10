package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.User; // Import User
import fptu.mobile_shop.MobileShop.entity.UserRole; // Import UserRole
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

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Giả định có phương thức này trong UserRepository
    }

    // Lấy người dùng theo ID
    public Optional<User> getUserById(Long id) { // Sửa kiểu ID cho đúng
        return userRepository.findById(id); // Giả định có phương thức này trong UserRepository
    }

    // Lưu hoặc cập nhật người dùng
    public User saveUser(User user) {
        return userRepository.save(user); // Giả định có phương thức này trong UserRepository
    }

    // Xóa người dùng dựa vào ID
    public void deleteUserById(Long id) { // Sửa kiểu ID cho đúng
        userRepository.deleteById(id); // Giả định có phương thức này trong UserRepository
    }
}
