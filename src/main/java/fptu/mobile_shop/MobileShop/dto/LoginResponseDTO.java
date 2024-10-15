package fptu.mobile_shop.MobileShop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class LoginResponseDTO {
    private String status;
    private String message;

    public LoginResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters and setters
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}

