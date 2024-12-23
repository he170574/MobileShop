package fptu.mobile_shop.MobileShop.service.impl;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Role;
import fptu.mobile_shop.MobileShop.final_attribute.ROLE;
import fptu.mobile_shop.MobileShop.repository.AccountRepository;
import fptu.mobile_shop.MobileShop.repository.RoleRepository;
import fptu.mobile_shop.MobileShop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static fptu.mobile_shop.MobileShop.service.fogotPassWord.javamail.SendHtmlEmail.generateRandomPassword;
import static fptu.mobile_shop.MobileShop.service.fogotPassWord.javamail.SendHtmlEmail.sendNewPassWordToEmail;
import static fptu.mobile_shop.MobileShop.service.fogotPassWord.javamail.SentMailToEmpCreate.sendAccountToEmailSTAFF;

@Service
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;
    RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    @Override
    public Account save(Account account) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        account.setRegisterDate(LocalDate.now());
        account.setDeleted(false);
        return accountRepository.save(account);
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("Viet123@"));
    }

    public void updateNewPassWord(String email) {
        String newPass = generateRandomPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String newPassBcrypt = bCryptPasswordEncoder.encode(newPass);
        accountRepository.updateAccountByEmail(newPassBcrypt, email);
        sendNewPassWordToEmail(newPass, email);
    }

    @Override
    @Transactional
    public Account getByUsername(String userName) {
        return accountRepository.getByUsernameAndDeletedIsFalse(userName);
    }


    @Transactional
    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }


    @Transactional
    @Override
    public int updateAccountByAccountUserName(String username, String newPass, String oldPassword) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Account account = accountRepository.getByUsernameAndDeletedIsFalse(username);

        if (!bCryptPasswordEncoder.matches(oldPassword, account.getPassword())) {
            return 0;
        }
        String newPassBcrypt = bCryptPasswordEncoder.encode(newPass);
        return accountRepository.updateAccountByAccountUserName(username, newPassBcrypt);
    }

    @Override
    public Page<Account> getAllSTAFF(String fullName, Pageable pageable) {
        Role role = roleRepository.getByRoleName(ROLE.STAFF);
        return accountRepository.getByFullNameContainsAndRoleAndDeletedIsFalse(fullName, role, pageable);
    }

    @Override
    public Account getAccountByAccountId(Integer accountId) {
        return accountRepository.getAccountByAccountId(accountId);
    }

    @Override
    public void deleteSTAFF(Account account) {
        accountRepository.deActiveAccount(account);
    }

    @Override
    public void sentEmailToEmp(String pass, String account, String gmail) {
        sendAccountToEmailSTAFF(pass, account, gmail);
    }

    @Override
    public int checkMailExist(String email) {
        boolean emailExists = accountRepository.existsByEmail(email);
        if (emailExists) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public List<Account> getAccountsByDeletedTrue() {
        // Lấy danh sách các Account đã bị đánh dấu là xóa
        List<Account> accounts = accountRepository.getAccountsByDeletedTrue();

        return accounts;
    }

    @Override
    public void activeSTAFF(Account account) {
        accountRepository.activeSTAFF(account);
    }
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> searchAccountsByName(String name) {
        return accountRepository.findByFullNameContaining(name);
    }

    public List<Account> filterAccountsByRole(String role) {
        return accountRepository.findByRoleRoleName(role);
    }

    public Optional<Account> getAccountById(Integer id) {
        return accountRepository.findById(id);
    }

    public Account saveOrUpdateAccount(Account account) {
        return accountRepository.save(account);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }
}

