package fptu.mobile_shop.MobileShop.dto;

import lombok.Data;

@Data  // Lombok tạo tự động getter, setter, toString, equals và hashCode
public class LoginDTO {
    private String email;
    private String password;
}

