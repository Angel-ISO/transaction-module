package org.jala.university.domain.repository;

import org.jala.university.domain.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID> {

    @Query(value = "SELECT count(*) FROM Transaction ")
    String totalTransactions();

    @Query("SELECT DATE(t.createdAt), SUM(t.amount) FROM Transaction t GROUP BY DATE(t.createdAt) ORDER BY DATE(t.createdAt)")
    List<Object[]> sumTransactionAmountByDate();

    @Query("SELECT t.transactionType, COUNT(t) FROM Transaction t GROUP BY t.transactionType")
    List<Object[]> countTransactionByType();

    @Query("SELECT DATE(t.createdAt) as date, t.currency.currencyCode as currencyCode, COUNT(t) as count " +
            "FROM Transaction t " +
            "GROUP BY DATE(t.createdAt), t.currency.currencyCode " +
            "ORDER BY DATE(t.createdAt)")
    List<Object[]> findTransactionCountsByCurrency();

}

