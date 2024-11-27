package org.jala.university.presentation.controller.admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jala.university.domain.entities.Account;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.UserService;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.DecimalFormatter;
import org.jala.university.presentation.utils.TransactionValidator;
import org.jala.university.presentation.utils.UserAccountDialogHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class AdUserController extends BaseController implements Initializable {

    @FXML
    private VBox userContainer;

    private final UserAccountDialogHelper userAccountDialogHelper;
    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public AdUserController(UserAccountDialogHelper userAccountDialogHelper, AccountService accountService, UserService userService) {
        this.userAccountDialogHelper = userAccountDialogHelper;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Cargar los datos iniciales de las cuentas
        List<Account> accounts = accountService.getAll();
        loadUserData(accounts);
    }

    private void loadUserData(List<Account> userAccounts) {
        userContainer.getChildren().clear();
        for (Account account : userAccounts) {
            if (account != null) {
                userContainer.getChildren().add(createUserBox(account));
            }
        }
    }

    private HBox createUserBox(Account account) {
        HBox userBox = new HBox();
        userBox.getStyleClass().add("user-box");

        Label status = createLabel(account.getStatus().toString());
        Label numberId = createLabel(account.getAccountNumber());
        Label balance = createLabel(String.valueOf(DecimalFormatter.roundNumber(account.getBalance())));
        Label user = createLabel(account.getUser().getUsername());
        Label accountType = createLabel(account.getAccountType().getTypeName());
        Label currencyName = createLabel(accountService.findCurrencyName(account.getUser().getId()));
        Label minAmount = TransactionValidator.createCurrencyLabel(account, true);
        Label maxAmount = TransactionValidator.createCurrencyLabel(account, false);

        Button deleteButton = createActionButton(FontAwesomeIcon.TRASH, "delete-button", e -> deleteUser(account));
        Button editButton = createActionButton(FontAwesomeIcon.EDIT, "edit-button", e -> editUser(account));

        userBox.getChildren().addAll(status, numberId, balance, user, accountType, currencyName, minAmount, maxAmount, deleteButton, editButton);

        return userBox;
    }

    private Button createActionButton(FontAwesomeIcon icon, String id, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        Button button = new Button();
        button.setId(id);
        button.getStyleClass().add("crud-button");

        FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
        iconView.setFill(javafx.scene.paint.Color.WHITE);
        button.setGraphic(iconView);
        button.setOnAction(action);

        return button;
    }

    private void deleteUser(Account account) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de Eliminación");
        alert.setHeaderText("Eliminar cuenta");
        alert.setContentText("¿Estás seguro de que deseas eliminar la cuenta con número: " + account.getAccountNumber() + "?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

            accountService.delete(account.getId());
            userService.delete(account.getUser().getId());

            userContainer.getChildren().removeIf(node -> {
                if (node instanceof HBox userBox) {
                    Label accountNumberLabel = (Label) userBox.getChildren().get(1); // Asume que el número está en la posición 1
                    return accountNumberLabel.getText().equals(account.getAccountNumber());
                }
                return false;
            });
        }
    }

    private void editUser(Account account) {
        Account updatedAccount = userAccountDialogHelper.showPartialEditDialog(account);
        if (updatedAccount != null) {
            accountService.updateAccount(updatedAccount);
            loadUserData(accountService.getAll());
        }
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("user-label");
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdUserController that = (AdUserController) o;
        return Objects.equals(userContainer, that.userContainer) && Objects.equals(userAccountDialogHelper, that.userAccountDialogHelper) && Objects.equals(accountService, that.accountService) && Objects.equals(userService, that.userService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userContainer, userAccountDialogHelper, accountService, userService);
    }
}
