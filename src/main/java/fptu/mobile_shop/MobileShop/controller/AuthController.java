package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.LoginDTO;
import fptu.mobile_shop.MobileShop.dto.LoginResponseDTO;
import fptu.mobile_shop.MobileShop.repository.ProductRepository;
import fptu.mobile_shop.MobileShop.service.IAutheticationService;
import fptu.mobile_shop.MobileShop.service.ProductService;
import fptu.mobile_shop.MobileShop.service.impl.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final IAutheticationService authenticationService;
    @Autowired
    public AuthController(IAutheticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginRequest) {
        LoginResponseDTO loginResponse = authenticationService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
}
