package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Account;
import org.jala.university.domain.entities.AccountStatus;
import org.jala.university.domain.entities.User;
import org.jala.university.domain.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void testGetAccountsByUserId() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        user.setId(userId);

        Account account1 = new Account();
        account1.setId(UUID.randomUUID());
        account1.setAccountNumber("123456789");
        account1.setUser(user);
        account1.setBalance(500.0);
        account1.setStatus(AccountStatus.ACTIVE);

        Account account2 = new Account();
        account2.setId(UUID.randomUUID());
        account2.setAccountNumber("987654321");
        account2.setUser(user);
        account2.setBalance(300.0);
        account2.setStatus(AccountStatus.INACTIVE);

        List<Account> expectedAccounts = List.of(account1, account2);

        when(accountRepository.findAccByUser_Id(userId)).thenReturn(expectedAccounts);

        List<Account> actualAccounts = accountService.getAccountsByUserId(userId);

        assertEquals(expectedAccounts, actualAccounts, "The accounts returned should match the expected list");
        verify(accountRepository, times(1)).findAccByUser_Id(userId);
    }
}
