package org.jala.university.presentation.controller.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jala.university.domain.entities.Transaction;
import org.jala.university.infrastructure.services.TransactionService;
import org.jala.university.presentation.commons.BaseController;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class HistoryController extends BaseController implements Initializable {


    private final TransactionService transactionService;

    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, String> userColumn;

    public HistoryController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTransactions();
    }

    private void loadTransactions() {
        List<Transaction> transactions;
        transactions = transactionService.getTransactions();
        transactionTable.getItems().setAll(transactions);
        userColumn.setCellValueFactory(cellData -> {
            Transaction transaction = cellData.getValue();
            if (transaction.getSourceAccount() != null && transaction.getSourceAccount().getUser() != null) {
                return new SimpleStringProperty(transaction.getSourceAccount().getUser().getUsername());
            } else {
                return new SimpleStringProperty("");
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HistoryController that = (HistoryController) o;
        return Objects.equals(transactionService, that.transactionService) && Objects.equals(transactionTable, that.transactionTable) && Objects.equals(userColumn, that.userColumn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transactionService, transactionTable, userColumn);
    }
}