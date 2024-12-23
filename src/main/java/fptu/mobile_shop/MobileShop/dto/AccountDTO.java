package fptu.mobile_shop.MobileShop.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {
    private Integer accountId;
    private String address;
    private LocalDate dateOfBirth;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String username;
    private String role;
    private LocalDate registerDate;
    private Boolean deleted;
    private String password;
    private MultipartFile imgFile;
    private int cartTotal;

    public AccountDTO(Integer accountId, String address, LocalDate dateOfBirth, String email, String fullName, String phoneNumber, String username, String role) {
        this.accountId = accountId;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.role = role;
    }

    public AccountDTO(String address, LocalDate dateOfBirth, String email, String fullName, String phoneNumber, String username, String role) {
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.role = role;
    }
}

