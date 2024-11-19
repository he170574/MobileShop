package fptu.mobile_shop.MobileShop.repository;

import fptu.mobile_shop.MobileShop.dto.jsonDTO.request.AccountFilterRequest;
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
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account getAccountByAccountId(Integer accountId);
    Account getByUsernameAndDeletedIsFalse(String userName);

    @Query("SELECT  a FROM Account a  WHERE a.email = ?1")
    Account findByEmailAndIsEnabledTrue(String email);
    boolean existsByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.password = :newPassword WHERE a.email = :email")
    void updateAccountByEmail(String newPassword, String email);

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

    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);

    @Query(value = """
            select distinct x1 from Account x1
            join x1.role x2
            where x1.deleted = false
            AND (:#{#request.keyword} is null or CONCAT(coalesce(x1.email,''), x1.fullName, x1.username,x1.phoneNumber) like CONCAT('%',:#{#request.keyword},'%'))
            AND ( :#{#request.roles.size} = 0 or x2.roleName in :#{#request.roles})
            """, countQuery = """
              select distinct  count(DISTINCT x1.accountId) from Account x1
                        join x1.role x2
                        where x1.deleted = false
                        AND (:#{#request.keyword} is null or CONCAT(coalesce(x1.email,''), x1.fullName, x1.username,x1.phoneNumber) like CONCAT('%',:#{#request.keyword},'%'))
                        AND ( :#{#request.roles.size} = 0 or x2.roleName in :#{#request.roles})
            """)
    Page<Account> getPageAccount(AccountFilterRequest request, Pageable pageable);
}
