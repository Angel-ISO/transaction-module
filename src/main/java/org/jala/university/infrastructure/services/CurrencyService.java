package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.Currency;
import org.jala.university.domain.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CurrencyService extends GenericService<Currency, UUID> {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CurrencyService(CurrencyRepository currencyRepository) {
        super(currencyRepository);
        this.currencyRepository = currencyRepository;
    }

    public List<String> getCurrenciesName() {
        return currencyRepository.findAll().stream().map(Currency::getCurrencyCode).toList();
    }

    public Currency findByCurrencyCode(String currencyCode) {
        return currencyRepository.findByCurrencyCode(currencyCode);
    }
}
