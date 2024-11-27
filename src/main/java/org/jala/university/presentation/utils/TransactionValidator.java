package org.jala.university.presentation.utils;

import javafx.scene.control.Label;
import org.jala.university.domain.entities.Account;
import static org.jala.university.presentation.utils.AlertMessage.showAlert;


    public class TransactionValidator {

        private TransactionValidator() {
        }

        public static boolean isAmountValid(Account account, double amount) {
            Double minAmount = account.getMinAmount();
            Double maxAmount = account.getMaxAmount();


            if (amount < minAmount) {
                showAlert("Amount Too Low", "The minimum transaction amount is " + minAmount + ".");
                return false;
            }
            if (amount > maxAmount) {
                showAlert("Amount Too High", "The maximum transaction amount is " + maxAmount + ".");
                return false;
            }

            return true;
        }

        public static Label createCurrencyLabel(Account account, boolean isMin) {
            double amount = isMin ? account.getMinAmount() : account.getMaxAmount();
            return new Label(String.valueOf(amount));
        }

    }

