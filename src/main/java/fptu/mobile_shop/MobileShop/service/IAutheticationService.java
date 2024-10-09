package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.dto.LoginDTO;
import fptu.mobile_shop.MobileShop.dto.LoginResponseDTO;

public interface IAutheticationService {
    LoginResponseDTO login(LoginDTO loginRequest);
}
