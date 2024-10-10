package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.dto.LoginDTO;
import fptu.mobile_shop.MobileShop.dto.LoginResponseDTO;
import fptu.mobile_shop.MobileShop.entity.User;
import fptu.mobile_shop.MobileShop.repository.UserRepository;
import fptu.mobile_shop.MobileShop.service.IAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginDTO loginRequest) {
        Optional<User> userOptional = userRepository.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
                // Tạo JWT token
                String token = jwtTokenUtil.generateToken(user.getEmail());
                return new LoginResponseDTO(token, user.getUserName());
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
}
