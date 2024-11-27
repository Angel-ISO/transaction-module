package org.jala.university.presentation.utils;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.jala.university.domain.entities.*;
import org.jala.university.infrastructure.services.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import static org.jala.university.presentation.utils.AlertMessage.showAlert;

@Component
public class UserAccountDialogHelper {

    @Autowired
    public UserAccountDialogHelper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    private final CurrencyService currencyService;


    public  Account showPartialEditDialog(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("The account must not be null");
        }

        Dialog<Account> dialog = new Dialog<>();
        dialog.setTitle("Edit Account");
        dialog.setHeaderText("Modify the necessary account fields.");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField balanceField = createNumericTextField(account.getBalance());
        TextField minField = createNumericTextField(account.getMinAmount());
        TextField maxField = createNumericTextField(account.getMaxAmount());

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("ACTIVE", "INACTIVE", "CLOSED");
        statusComboBox.setValue(account.getStatus().toString());


        ComboBox<String> accountTypeCurrency = new ComboBox<>();
        accountTypeCurrency.getItems().addAll("USD", "EUR", "COP");
        accountTypeCurrency.setValue(account.getCurrency().getCurrencyCode());

        gridPane.add(new Label("Balance:"), 0, 0);
        gridPane.add(balanceField, 1, 0);
        gridPane.add(new Label("Status:"), 0, 1);
        gridPane.add(statusComboBox, 1, 1);
        gridPane.add(new Label("Currency Type:"), 0, 2);
        gridPane.add(accountTypeCurrency, 1, 2);
        gridPane.add(new Label("Min Amount Transaction:"), 0, 3);
        gridPane.add(minField, 1, 3);
        gridPane.add(new Label("Max Amount:"), 0, 4);
        gridPane.add(maxField, 1, 4);

        dialog.getDialogPane().setContent(gridPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    account.setBalance(parseDouble(balanceField.getText()));
                    account.setMinAmount(parseDouble(minField.getText()));
                    account.setMaxAmount(parseDouble(maxField.getText()));
                    account.setCurrency(currencyService.findByCurrencyCode(accountTypeCurrency.getValue()));
                    account.setStatus(AccountStatus.valueOf(statusComboBox.getValue()));
                    System.out.println("Se hizo el cambio de la cuenta" + account.getMaxAmount() +"\n" + account.getMinAmount());
                    return account;
                } catch (IllegalArgumentException ex) {
                    showAlert("Please enter valid numbers in all fields.", "Input Error");
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private  TextField createNumericTextField(double initialValue) {
        TextField textField = new TextField(String.valueOf(initialValue));
        textField.setPromptText("Enter a number");
        return textField;
    }

    private  double parseDouble(String text) {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be empty");
        }
        return Double.parseDouble(text.replace(",", "."));
    }

}
