package org.jala.university.domain.repository;

import org.jala.university.domain.entities.Account;
import org.jala.university.domain.entities.AccountType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends BaseRepository<Account, UUID> {

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    List<Account> findAccByUser_Id(UUID userId);

    @Query("SELECT a FROM Account a WHERE a.user.id = :userId")
    Account findAmountByUserId(UUID userId);

    @Query("SELECT a.status FROM Account a WHERE a.id = :accountId")
    String findStatusByAccountId(UUID accountId);

    @Query("SELECT a.currency.currencyCode FROM Account a WHERE a.id = :accountId")
    String findCurrencyNameByAccountId(UUID accountId);

    @Query("UPDATE Account a SET a.accountType = :accountType WHERE a.id = :accountId")
    void updateAccountType(@Param("accountId") UUID accountId, @Param("accountType") AccountType accountType);

    @Query("SELECT a FROM Account a WHERE a.user.email = :email")
    Account findAccountByEmailUser(String email);

    @Query("UPDATE Account a SET a.status = :status, a.balance = :balance, a.accountType = :accountType WHERE a.id = :accountId")
    void updateAccount(UUID accountId, String status, double balance, AccountType accountType);

    @Query("SELECT a.status FROM Account a WHERE a.user.id = :userId")
    String findStatusByUserId(UUID userId);

    @Query("UPDATE Account a SET a.minAmount = :minAmount, a.maxAmount = :maxAmount WHERE a.id = :accountId")
    void updateMinMaxAmounts(UUID accountId, Double minAmount, Double maxAmount);

    @Query("SELECT a.user.email FROM Account a WHERE a.id = :accountId")
    Optional<String> findEmailByAccountId(UUID accountId);

    @Query("SELECT a.user.username FROM Account a WHERE a.id = :accountId")
    Optional<String> findNameUserByAccountId(UUID accountId);

    @Query("SELECT a.id FROM Fee a WHERE a.id = :feeId")
    String findIdByFeeId(UUID feeId);
    @Query("SELECT count(*) FROM Account ")
    String totalUsers();
}

