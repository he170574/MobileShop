package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.entity.User1;
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
    public String registerUser(@ModelAttribute User1 user, @RequestParam("password") String rawPassword) {
        authenticationService.register(user, rawPassword);
        return "redirect:/auth/login";
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // Trả về view đăng nhập
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        // Tìm người dùng theo email
        User user = authenticationService.findByEmail(email);

        if (user == null) {
            // Nếu không tìm thấy người dùng, thêm thông báo lỗi
            model.addAttribute("error", "Tài khoản không tồn tại.");
            return "login"; // Trả về lại trang đăng nhập
        }

        // Kiểm tra mật khẩu
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            // Nếu mật khẩu không khớp, thêm thông báo lỗi
            model.addAttribute("error", "Sai mật khẩu.");
            return "login"; // Trả về lại trang đăng nhập
        }

        // Nếu thành công, chuyển hướng về trang chính
        return "redirect:/home";
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // Hủy session
        return "redirect:/auth/login"; // Chuyển hướng đến trang đăng nhập
    }
}
