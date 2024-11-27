package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Account;
import org.jala.university.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AccountService extends GenericService<Account, UUID> {

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
    }

    private final AccountRepository accountRepository;

    public List<Account> getAccountsByUserId(UUID userId) {
        return accountRepository.findAccByUser_Id(userId);
    }

    public Account findAmount(UUID userId) {
        return accountRepository.findAmountByUserId(userId);
    }

    public String findStatusByAccountId(UUID accountId) {
        return accountRepository.findStatusByAccountId(accountId);
    }

    public Account findAccountByEmailUser(String email) {
        return accountRepository.findAccountByEmailUser(email);
    }

    public String findCurrencyName(UUID accountId) {
        return accountRepository.findCurrencyNameByAccountId(accountId);
    }

    public String findIdByFeeId(UUID feeId) {
        return accountRepository.findIdByFeeId(feeId);
    }
    
    public String findStatusByUserId(UUID userId) {
        return accountRepository.findStatusByUserId(userId);
    }

    public void updateAccount(Account updatedAccount) {
        Account existingAccount = accountRepository.findById(updatedAccount.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada con ID: " + updatedAccount.getId()));

        existingAccount.setStatus(updatedAccount.getStatus());
        existingAccount.setBalance(updatedAccount.getBalance());
        existingAccount.setAccountType(updatedAccount.getAccountType());
        existingAccount.setCurrency(updatedAccount.getCurrency());
        existingAccount.setMinAmount(updatedAccount.getMinAmount());
        existingAccount.setMaxAmount(updatedAccount.getMaxAmount());

        accountRepository.save(existingAccount);
    }

    public String getTotalUsers() {
        return accountRepository.totalUsers();
    }
}


