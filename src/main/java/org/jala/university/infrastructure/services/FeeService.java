package org.jala.university.infrastructure.services;

import org.jala.university.domain.entities.ApplicableTo;
import org.jala.university.domain.entities.Currency;
import org.jala.university.domain.entities.Fee;
import org.jala.university.domain.repository.FeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FeeService extends GenericService<Fee, UUID> {

    @Autowired
    public FeeService(FeeRepository feeRepository, TransactionService transactionService) {
            super(feeRepository);
            this.feeRepository = feeRepository;
            this.transactionService = transactionService;
        }

        private final FeeRepository feeRepository;
        private final TransactionService transactionService;

        private static final String ACCOUNT_MASTER = "angelg.ortegac@iensb.edu.co";
        public static final double FEE_AMOUNT_DOMESTIC = 0.015;
        public static final double FEE_AMOUNT_INTERNATIONAL = 0.03;
        public static final String DESCRIPTION_FEE = "Fee do for the user: ";

        public void createFee(String sourceEmail, String feeName, double amount, String description,Currency sourceCurrency, Currency destinationCurrency ) {
            ApplicableTo typeFee = sourceCurrency.equals(destinationCurrency) ? ApplicableTo.DOMESTIC : ApplicableTo.INTERNATIONAL;
            transactionService.createTransaction(sourceEmail,ACCOUNT_MASTER, amount, description);
            Fee fee = new Fee();
            fee.setFeeName(feeName);
            fee.setAmount(amount);
            fee.setDescription(DESCRIPTION_FEE + sourceEmail);
            fee.setApplicableTo(typeFee);
            feeRepository.save(fee);
        }

        public double getTotalFees() {
            return feeRepository.totalFees();
        }

        public List<Object[]> getSumFeesByType() {
            return feeRepository.sumFeesByType();
        }

        public Map<String, Double> getFeesByType() {
            List<Object[]> results = feeRepository.sumFeesByType();
            Map<String, Double> feesByType = new HashMap<>();
            for (Object[] result : results) {
                feesByType.put(result[0].toString(), (Double) result[1]);
            }
            return feesByType;
        }
    }