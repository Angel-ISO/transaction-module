package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.*;
import org.jala.university.domain.repository.AccountRepository;
import org.jala.university.domain.repository.TransactionRepository;
import org.jala.university.infrastructure.http.ExchangeRateResponse;
import org.jala.university.infrastructure.http.ExchangeRateService;
import org.jala.university.presentation.utils.DecimalFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService extends GenericService<Transaction, UUID> {


    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ExchangeRateService exchangeRateService, AccountRepository accountRepository, NotificationService notificationService) {
        super(transactionRepository);
        this.transactionRepository = transactionRepository;
        this.exchangeRateService = exchangeRateService;
        this.accountRepository = accountRepository;
        this.notificationService = notificationService;
    }

    private final TransactionRepository transactionRepository;
    private final ExchangeRateService exchangeRateService;
    private final AccountRepository accountRepository;
    private final NotificationService notificationService;


    public void createTransaction(String sourceEmail, String destinationEmail, double amount, String description) {
        Account sourceAccount = accountRepository.findAccountByEmailUser(sourceEmail);
        Account destinationAccount = accountRepository.findAccountByEmailUser(destinationEmail);

        if (sourceAccount == null || destinationAccount == null) {
            throw new IllegalArgumentException("Source or destination account not found.");
        }
        amount = DecimalFormatter.roundNumber(amount);
        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setDescription(description);
        transaction.setAmount(amount);
        transaction.setStatus(TransactionStatus.COMPLETED);
        transaction.setCurrency(sourceAccount.getCurrency());
        transaction.setTransactionType(TransactionType.DEPOSIT);
        if (sourceAccount.getCurrency().equals(destinationAccount.getCurrency())) {
            processTransactionSameCurrency(transaction);
        } else {
            createTransactionWithCurrencyConversion(transaction);
        }
        createAndSendNotification(sourceAccount.getId(), destinationAccount.getId(), amount);
    }

    private void processTransactionSameCurrency(Transaction transaction) {
        Account sourceAccount = transaction.getSourceAccount();
        Account destinationAccount = transaction.getDestinationAccount();

        if (sourceAccount.getBalance() < transaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in the source account.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
        accountRepository.save(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
        accountRepository.save(destinationAccount);

        transactionRepository.save(transaction);
    }

    private void createTransactionWithCurrencyConversion(Transaction transaction) {
        String sourceCurrencyCode = transaction.getSourceAccount().getCurrency().getCurrencyCode();
        String destinationCurrencyCode = transaction.getDestinationAccount().getCurrency().getCurrencyCode();

        ExchangeRateResponse exchangeRateResponse = exchangeRateService.getExchangeRates(sourceCurrencyCode);
        BigDecimal exchangeRate = exchangeRateResponse.getRates().get(destinationCurrencyCode);

        if (exchangeRate == null) {
            throw new IllegalArgumentException("Exchange rate not available for the destination currency.");
        }

        BigDecimal convertedAmount = exchangeRate.multiply(BigDecimal.valueOf(transaction.getAmount()));

        Account sourceAccount = transaction.getSourceAccount();
        Account destinationAccount = transaction.getDestinationAccount();

        if (sourceAccount.getBalance() < transaction.getAmount()) {
            throw new IllegalArgumentException("Insufficient funds in the source account.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
        accountRepository.save(sourceAccount);

        destinationAccount.setBalance(destinationAccount.getBalance() + convertedAmount.doubleValue());
        accountRepository.save(destinationAccount);

        transaction.setAmount(convertedAmount.doubleValue());
        transaction.setCurrency(destinationAccount.getCurrency());
        transactionRepository.save(transaction);
    }
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    private void createAndSendNotification(UUID sourceAccountId, UUID destinationAccountId, double amount) {
        Notification notification = notificationService.createNotification(sourceAccountId, destinationAccountId, amount);
    }

    public String getTotalTransactions() {
        return transactionRepository.totalTransactions();
    }

    public Map<String, Long> getTransactionCountByType() {
        List<Object[]> results = transactionRepository.countTransactionByType();
        Map<String, Long> transactionsByType = new HashMap<>();
        for (Object[] result : results) {
            transactionsByType.put(result[0].toString(), (Long) result[1]);
        }
        return transactionsByType;
    }

    public Map<String, Double> getTransactionAmountsByDate() {
        List<Object[]> results = transactionRepository.sumTransactionAmountByDate();
        Map<String, Double> amountsByDate = new LinkedHashMap<>();
        for (Object[] result : results) {
            amountsByDate.put(result[0].toString(), (Double) result[1]);
        }
        return amountsByDate;
    }

    public Map<String, Long> getTransactionCountByCurrency() {
        List<Object[]> rawData = transactionRepository.findTransactionCountsByCurrency();
        Map<String, Long> result = new HashMap<>();

        for (Object[] row : rawData) {
            String currencyCode = (String) row[1];
            Long count = ((Number) row[2]).longValue();
            result.put(currencyCode, result.getOrDefault(currencyCode, 0L) + count);
        }

        return result;
    }

}


