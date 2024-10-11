package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PasswordEncoder passwordEncoder; // Để mã hóa mật khẩu
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // Trả về view đăng ký
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        authenticationService.register(user);
        return "redirect:/auth/login"; // Chuyển hướng đến trang đăng nhập
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Trả về view đăng nhập
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        // Tìm người dùng theo email
        User user = authenticationService.findByEmail(email);

        // Kiểm tra xem người dùng có tồn tại và mật khẩu có khớp không
        if (user != null && passwordEncoder.matches(password, user.getPasswordHash())) {
            // Đăng nhập thành công
            return "redirect:/home"; // Chuyển hướng đến trang chính
        } else {
            // Đăng nhập thất bại
            return "redirect:/auth/login?error"; // Chuyển hướng đến trang đăng nhập với thông báo lỗi
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // Hủy session
        return "redirect:/auth/login"; // Chuyển hướng đến trang đăng nhập
    }
}
