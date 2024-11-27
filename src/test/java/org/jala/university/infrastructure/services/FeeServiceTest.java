package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Currency;
import org.jala.university.domain.entities.Fee;
import org.jala.university.domain.repository.FeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeeServiceTest {

    @Mock
    private FeeRepository feeRepository;

    @Mock
    private TransactionService transactionService;

    private FeeService feeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        feeService = new FeeService(feeRepository, transactionService);
    }

    @Test
    void testCreateFee() {
        String sourceEmail = "test@example.com";
        String feeName = "Test Fee";
        double amount = 100.0;
        String description = "Test Description";
        Currency sourceCurrency = new Currency();
        sourceCurrency.setCurrencyCode("USD");

        Currency destinationCurrency = new Currency();
        destinationCurrency.setCurrencyCode("USD");

        feeService.createFee(sourceEmail, feeName, amount, description, sourceCurrency, destinationCurrency);

        verify(transactionService, times(1)).createTransaction(
                eq(sourceEmail), eq("angelg.ortegac@iensb.edu.co"), eq(amount), eq(description)
        );

        verify(feeRepository, times(1)).save(any(Fee.class));
    }

    @Test
    void testGetTotalFees() {
        double totalFees = 500.0;
        when(feeRepository.totalFees()).thenReturn(totalFees);

        double result = feeService.getTotalFees();

        assertEquals(totalFees, result, "Total fees should match the repository value");
        verify(feeRepository, times(1)).totalFees();
    }

    @Test
    void testGetFeesByType() {
        List<Object[]> sumByType = List.of(
                new Object[]{"DOMESTIC", 200.0},
                new Object[]{"INTERNATIONAL", 300.0}
        );

        when(feeRepository.sumFeesByType()).thenReturn(sumByType);

        Map<String, Double> feesByType = feeService.getFeesByType();

        assertEquals(2, feesByType.size(), "Should contain two fee types");
        assertEquals(200.0, feesByType.get("DOMESTIC"), "Domestic fees should match");
        assertEquals(300.0, feesByType.get("INTERNATIONAL"), "International fees should match");
        verify(feeRepository, times(1)).sumFeesByType();
    }
}
