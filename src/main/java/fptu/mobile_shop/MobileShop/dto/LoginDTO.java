package fptu.mobile_shop.MobileShop.dto;

import lombok.Data;

@Data  // Lombok tạo tự động getter, setter, toString, equals và hashCode
public class LoginDTO {
    private String email;
    private String password;

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

