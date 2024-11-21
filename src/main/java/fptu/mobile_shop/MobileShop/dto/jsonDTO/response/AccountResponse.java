package fptu.mobile_shop.MobileShop.dto.jsonDTO.response;

import fptu.mobile_shop.MobileShop.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer accountId;
    private String address;
    private LocalDate dateOfBirth;
    private String email;
    private String fullName;
    private String phoneNumber;
    private LocalDate registerDate;
    private String username;
    private Boolean deleted;
    private RoleResponse role;

    public AccountResponse(Account account) {
        this.accountId = account.getAccountId();
        this.address = account.getAddress();
        this.dateOfBirth = account.getDateOfBirth();
        this.email = account.getEmail();
        this.fullName = account.getFullName();
        this.phoneNumber = account.getPhoneNumber();
        this.registerDate = account.getRegisterDate();
        this.username = account.getUsername();
        this.deleted = account.getDeleted();
        this.role = account.getRole() != null ? new RoleResponse(account.getRole()) : null;
    }
}
