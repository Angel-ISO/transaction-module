package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Transaction;
import org.jala.university.domain.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    public TransactionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactionsReturnsAllTransactions() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.0);
        List<Transaction> mockTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findAll()).thenReturn(mockTransactions);

        List<Transaction> transactions = transactionService.getTransactions();

        assertEquals(2, transactions.size());
        assertEquals(100.0, transactions.get(0).getAmount());
        assertEquals(200.0, transactions.get(1).getAmount());
    }

    @Test
    public void testGetTransactionsThrowsException() {
        when(transactionRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        try {
            transactionService.getTransactions();
        } catch (RuntimeException e) {
            assertEquals("Database error", e.getMessage());
        }
    }

    @Test
    public void testGetTransactionsReturnsEmptyList() {
        when(transactionRepository.findAll()).thenReturn(Arrays.asList());

        List<Transaction> transactions = transactionService.getTransactions();

        assertEquals(0, transactions.size());
    }


}
