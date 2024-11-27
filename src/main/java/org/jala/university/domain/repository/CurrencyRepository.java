package org.jala.university.domain.repository;

import org.jala.university.domain.entities.Currency;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CurrencyRepository extends BaseRepository<Currency, UUID> {

    @Query("Select a from Currency a where a.currencyCode = :currencyCode")
    Currency findByCurrencyCode(String currencyCode);
}


