package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.entity.Account;
import fptu.mobile_shop.MobileShop.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountByAccountId(Integer accountId);
    Account getByUsernameAndDeletedIsFalse(String userName);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :newPassword WHERE a.email = :email")
    void updateAccountByEmail(String newPassword, String email);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.image = :newImage WHERE a.username = :username")
    void updateAccountImage(@Param("username") String username, @Param("newImage") String newImage);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :newPass WHERE a.username = :username")
    int updateAccountByAccountUserName(String username,String newPass);

    @Transactional
    Page<Account> getByFullNameContainsAndRoleAndDeletedIsFalse(String fullName, Role role, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Account a set a.deleted = true WHERE a = :account")
    void deActiveAccount(Account account);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Account a WHERE LOWER(a.email) = LOWER(:email)")
    boolean existsByEmail(@Param("email") String email);

    List<Account> getAccountsByDeletedTrue();
    @Transactional
    @Modifying
    @Query("UPDATE Account a set a.deleted = false WHERE a = :account")
    void activeSTAFF(Account account);

}
