package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.jala.university.domain.entities.Account;
import org.jala.university.domain.entities.Currency;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.FeeService;
import org.jala.university.infrastructure.services.TransactionService;
import org.jala.university.presentation.auth.UserSession;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.AlertMessage;
import org.jala.university.presentation.utils.DecimalFormatter;
import org.jala.university.presentation.utils.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


@Component
public class SendMoneyController extends BaseController implements Initializable {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final FeeService feeService;

    private static final String MESSAGE_FAIL = "Transaction Failed";

    @FXML
    private Label status;

    @FXML
    private Label currentAmount;

    @FXML
    private Label currencyType;

    @FXML
    private TextField destinationEmailField;
    @FXML
    private TextField amountField;
    @FXML
    private TextField descriptionField;

    @Autowired
    public SendMoneyController(AccountService accountService, TransactionService transactionService, FeeService feeService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.feeService = feeService;
    }

    public void updateCurrentAmount() {
        String balance = "$" + DecimalFormatter.roundNumber(accountService
                .findAmount(UserSession.getInstance().getLoggedUser().getId())
                .getBalance());
        String statusUser = accountService.findStatusByUserId(UserSession.getInstance().getLoggedUser().getId());
        String currencyName = accountService.findCurrencyName(UserSession.getInstance().getLoggedUser().getId());

        currentAmount.setText(balance);
        currencyType.setText(currencyName);
        status.setText(statusUser);

    }

    @FXML
    private void createTransaction() {
        String destinationEmail = destinationEmailField.getText();
        String description = descriptionField.getText();
        double totalAmount;
        double feeAmount;
        double amountToSend;

        Account sourceAccount = accountService.getAccountsByUserId(UserSession.getInstance().getLoggedUser().getId()).get(0);
        Account destnationAccount = accountService.findAccountByEmailUser(destinationEmail);

        Currency sourceCurrency = sourceAccount.getCurrency();
        Currency destinationCurrency = destnationAccount.getCurrency();
        boolean isDomestic = sourceCurrency.equals(destinationCurrency);
        double feeRate = isDomestic ? FeeService.FEE_AMOUNT_DOMESTIC : FeeService.FEE_AMOUNT_INTERNATIONAL;

        try {
            totalAmount = Double.parseDouble(amountField.getText().replace(",", ""));
            feeAmount = totalAmount * feeRate;
            amountToSend = totalAmount - feeAmount;

            if (!TransactionValidator.isAmountValid(sourceAccount, totalAmount)) {
                AlertMessage.showAlert("Insufficient balance.", "You don't have enough balance to cover the amount and fee.");
                return;
            }
        } catch (NumberFormatException e) {
            AlertMessage.showAlert("Invalid Amount", "Please enter a valid number for the amount.");
            return;
        }

        if (!isAccountValid()) {
            return;
        }

        try {
            String sourceEmail = UserSession.getInstance().getLoggedUser().getEmail();

            String transactionType = isDomestic ? "Domestic" : "International";
            boolean isConfirm = AlertMessage.acceptConfirmAlert(
                    "Do you want to send money?",
                    "Transaction Type: " + transactionType + "\n" +
                            "You are about to send " + amountToSend + " with a fee of " + DecimalFormatter.roundNumber(feeAmount) +
                            ". Total deducted: " + totalAmount + ". Are you sure?"
            );
            if (!isConfirm) {
                return;
            }

            transactionService.createTransaction(sourceEmail, destinationEmail, amountToSend, description);
            feeService.createFee(sourceEmail, "Transaction Fee", DecimalFormatter.roundNumber(feeAmount), description, sourceCurrency, destinationCurrency);

            AlertMessage.showAlert("Transaction Success", "Money sent successfully!");
            destinationEmailField.clear();
            amountField.clear();
            descriptionField.clear();

            updateCurrentAmount();
        } catch (Exception e) {
            AlertMessage.showAlert(MESSAGE_FAIL, e.getMessage());
        }
    }

    private boolean isAccountValid() {
        String statusAccount = accountService.findStatusByUserId(UserSession.getInstance().getLoggedUser().getId());
        if (statusAccount.equals("CLOSED")) {
            AlertMessage.showAlert(MESSAGE_FAIL, "Your account is closed.");
            return false;
        } else if (statusAccount.equals("INACTIVE")) {
            AlertMessage.showAlert(MESSAGE_FAIL, "Your account is inactive.");
            return false;
        }

        if (!UserSession.getInstance().isOtpVerified()) {
            AlertMessage.showAlert(MESSAGE_FAIL, "You must authenticate before sending money. Visit the QR section.");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateCurrentAmount();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SendMoneyController that = (SendMoneyController) o;
        return Objects.equals(transactionService, that.transactionService) && Objects.equals(accountService, that.accountService) && Objects.equals(feeService, that.feeService) && Objects.equals(status, that.status) && Objects.equals(currentAmount, that.currentAmount) && Objects.equals(currencyType, that.currencyType) && Objects.equals(destinationEmailField, that.destinationEmailField) && Objects.equals(amountField, that.amountField) && Objects.equals(descriptionField, that.descriptionField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactionService, accountService, feeService, status, currentAmount, currencyType, destinationEmailField, amountField, descriptionField);
    }
}
