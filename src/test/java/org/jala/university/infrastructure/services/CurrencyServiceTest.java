package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Currency;
import org.jala.university.domain.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CurrencyServiceTest {

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        currencyService = new CurrencyService(currencyRepository);
    }

    @Test
    void testGetCurrenciesName() {
        Currency currency1 = new Currency();
        currency1.setId(UUID.randomUUID());
        currency1.setCurrencyCode("USD");

        Currency currency2 = new Currency();
        currency2.setId(UUID.randomUUID());
        currency2.setCurrencyCode("EUR");

        List<Currency> currencies = List.of(currency1, currency2);
        when(currencyRepository.findAll()).thenReturn(currencies);

        List<String> currencyNames = currencyService.getCurrenciesName();

        assertEquals(List.of("USD", "EUR"), currencyNames, "Currency codes should match the expected values");
        verify(currencyRepository, times(1)).findAll();
    }

    @Test
    void testFindByCurrencyCode() {
        String currencyCode = "USD";
        Currency currency = new Currency();
        currency.setId(UUID.randomUUID());
        currency.setCurrencyCode(currencyCode);

        when(currencyRepository.findByCurrencyCode(currencyCode)).thenReturn(currency);

        Currency result = currencyService.findByCurrencyCode(currencyCode);

        assertEquals(currency, result, "The returned currency should match the expected currency");
        verify(currencyRepository, times(1)).findByCurrencyCode(currencyCode);
    }
}
