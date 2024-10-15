package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.entity.User1;
import fptu.mobile_shop.MobileShop.entity.UserRole;
import fptu.mobile_shop.MobileShop.repository.User1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private User1Repository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void register(User1 user, String rawPassword) {
        // Mã hóa mật khẩu
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPasswordHash(encodedPassword);

        // Tạo đối tượng UserRole với RoleID là 4 (ví dụ: vai trò mặc định là "user")
        UserRole defaultRole = new UserRole();


        // Lưu user vào cơ sở dữ liệu
        userRepository.save(user);
    }




    public User1 login(String email, String password) {
        User1 user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
    public User1 findByEmail(String email) {
        return userRepository.findByEmail(email); // Truy vấn người dùng theo email
    }
}

