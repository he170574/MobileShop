package fptu.mobile_shop.MobileShop.controller;

import fptu.mobile_shop.MobileShop.dto.AccountDTO;
import fptu.mobile_shop.MobileShop.dto.ChangePasswordDTO;
import fptu.mobile_shop.MobileShop.dto.ResponseDTO;
import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.final_attribute.ROLE;
import fptu.mobile_shop.MobileShop.security.CustomAccount;
import fptu.mobile_shop.MobileShop.service.AccountService;
import fptu.mobile_shop.MobileShop.service.CartService;
import fptu.mobile_shop.MobileShop.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RestController
public class AccountController {
    @Resource
    AccountService accountService;
    @Resource
    RoleService roleService;
    @Resource
    CartService cartService;

    @Autowired
    public AccountController(AccountService accountService, RoleService roleService) {
        this.accountService = accountService;
        this.roleService = roleService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> postRegister(@RequestBody AccountDTO accountDTO) {
        Account account = Account.builder()
                .dateOfBirth(accountDTO.getDateOfBirth())
                .email(accountDTO.getEmail())
                .fullName(accountDTO.getFullName())
                .password(accountDTO.getPassword())
                .phoneNumber(accountDTO.getPhoneNumber())
                .username(accountDTO.getUsername())
                .build();
        try {
            account.setRole(roleService.getRoleByRoleName(ROLE.MEMBER));
            accountService.save(account);
            return ResponseEntity.ok().body(ResponseDTO.builder().message("Register Success").build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @GetMapping("/get-account-using")
    public ResponseEntity<ResponseDTO> getAccountUsing(Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (authentication == null) {
                responseDTO.setMessage("No Login");
            } else {

                responseDTO.setMessage("Success");
                CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
                Account account = accountService.getByUsername(customAccount.getUsername());

                AccountDTO accountDTO = new AccountDTO(
                        account.getAccountId(),
                        account.getAddress(),
                        account.getDateOfBirth(),
                        account.getEmail(),
                        account.getFullName(),
                        account.getPhoneNumber(),
                        account.getUsername(),
                        account.getRole().getRoleName());

                accountDTO.setCartTotal(cartService.getCartTotal(account));

                responseDTO.setData(accountDTO);
            }
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(responseDTO);
        }

    }

    @PostMapping("/update-account")
    public ResponseEntity<ResponseDTO> updateAccount(@RequestBody AccountDTO accountDTO,
                                                     Authentication authentication) {
        try {
            if (authentication == null) {
                // Check Login
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDTO.builder().message("Please Login!").build());
            }
            CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
            Account account = accountService.getByUsername(accountDTO.getUsername());

            if ((!accountDTO.getUsername().equalsIgnoreCase(customAccount.getUsername()) ||

                    accountDTO.getRole() != null)) {
                // If update important filed, need to check
                if (customAccount.getRole().equals(ROLE.ADMIN)) {
                    // Is Admin
                    account.setRole(roleService.getRoleByRoleName(accountDTO.getRole()));
                    account.setUsername(accountDTO.getUsername());
                } else {
                    // Not Admin
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(ResponseDTO.builder().message("No permission to update").build());
                }
            }

            account.setAddress(accountDTO.getAddress());
            account.setDateOfBirth(accountDTO.getDateOfBirth());
            account.setEmail(accountDTO.getEmail());
            account.setFullName(accountDTO.getFullName());
            account.setPhoneNumber(accountDTO.getPhoneNumber());

            accountService.updateAccount(account);
            return ResponseEntity.ok().body(ResponseDTO.builder().message("Update Success").build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseDTO.builder().message(e.getMessage()).build());
        }
    }

    @PostMapping("/forgot-pass")
    public ResponseEntity<ResponseDTO> getNewPassWord(@RequestBody String email) {
        // Kiểm tra xem email có tồn tại không
        if (accountService.checkMailExist(email) == 0) {
            return ResponseEntity.badRequest().body(ResponseDTO.builder().message("Email không tồn tại").build());
        }

        // Gửi email với mật khẩu mới
        try {
            accountService.updateNewPassWord(email);
            return ResponseEntity.ok().body(ResponseDTO.builder().message("Gửi email thành công").build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseDTO.builder().message("Gửi email thất bại: " + e.getMessage()).build());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<ResponseDTO> changePassWord(Authentication authentication,
                                                      @RequestBody ChangePasswordDTO changePasswordDTO) {
        CustomAccount account = (CustomAccount) authentication.getPrincipal();

        int numberRowEffect = accountService.updateAccountByAccountUserName(account.getUsername(),
                changePasswordDTO.getNewPassword(), changePasswordDTO.getOldPassword());

        if (numberRowEffect > 0) {
            return ResponseEntity.ok().body(ResponseDTO.builder().message("change password success").build());
        }
        return ResponseEntity.badRequest()
                .body(ResponseDTO.builder().message("change password fail").data("Old Password Wrong").build());
    }

    @PostMapping("/toggle-account-status")
    public ResponseEntity<ResponseDTO> toggleAccountStatus(@RequestParam Integer accountId, Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CustomAccount customAccount = (CustomAccount) authentication.getPrincipal();
            Account account = accountService.getAccountByAccountId(accountId);

            if (customAccount.getRole().equals(ROLE.ADMIN)) {
                // Admin không thể thay đổi trạng thái của tài khoản
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(ResponseDTO.builder().message("Admin cannot change account status").build());
            }

            if (account.getDeleted()) {
                account.setDeleted(false); // Kích hoạt
            } else {
                account.setDeleted(true); // Hủy kích hoạt
            }
            accountService.updateAccount(account);
            responseDTO.setMessage("Account status updated successfully");
            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setMessage("Internal Server Error");
            return ResponseEntity.internalServerError().body(responseDTO);
        }
    }
}
