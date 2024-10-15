package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.LoginDTO;
import fptu.mobile_shop.MobileShop.dto.LoginResponseDTO;
import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.entity.User1;
import fptu.mobile_shop.MobileShop.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            User1 user = authenticationService.login(loginDTO.getEmail(), loginDTO.getPassword());
            if (user != null) {
                return ResponseEntity.ok(new LoginResponseDTO("success", "Đăng nhập thành công"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponseDTO("error", "Email hoặc mật khẩu không đúng"));
            }
        } catch (Exception e) {
            e.printStackTrace(); // Ghi log lỗi để kiểm tra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new LoginResponseDTO("error", "Đã xảy ra lỗi trong quá trình xử lý"));
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // Hủy session
        return "redirect:/auth/login"; // Chuyển hướng đến trang đăng nhập
    }
}
