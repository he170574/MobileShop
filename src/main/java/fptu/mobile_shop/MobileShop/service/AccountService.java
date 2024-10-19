package fptu.mobile_shop.MobileShop.service;

import fptu.mobile_shop.MobileShop.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    Account save(Account account);

    void updateNewPassWord(String email);

    Account getByUsername(String userName);

    void updateAccount(Account account);

    int updateAccountByAccountUserName(String username, String newPass, String oldPassword);

    Page<Account> getAllSTAFF(String fullName, Pageable pageable);

    Account getAccountByAccountId(Integer accountId);

    void deleteSTAFF(Account account);

    void sentEmailToEmp(String pass, String account, String gmail);

    int checkMailExist(String email);

    List<Account> getAccountsByDeletedTrue();

    void activeSTAFF(Account account);

}

