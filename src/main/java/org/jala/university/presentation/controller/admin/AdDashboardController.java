package org.jala.university.presentation.controller.admin;

import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.FeeService;
import org.jala.university.infrastructure.services.TransactionService;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.DecimalFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class AdDashboardController extends BaseController implements Initializable {

    private final AccountService accountService;
    private final FeeService feeService;
    private final TransactionService transactionService;
    private static final String EMAIL_ADMIN = "angelg.ortegac@iensb.edu.co";


    @FXML
    private Label amountAdmin;

    @FXML
    private PieChart categoryPieChart;

    @FXML
    private Label totalCommissionsLabel;

    @FXML
    private Label totalTransactionsLabel;

    @FXML
    private Label totalUsersLabel;

    @FXML
    private LineChart<String, Double> transactionsLineChart;

    @Autowired
    public AdDashboardController(AccountService accountService, FeeService feeService, org.jala.university.infrastructure.services.TransactionService transactionService) {
        this.accountService = accountService;
        this.feeService = feeService;
        this.transactionService = transactionService;
    }

    private void updateInfomation() {
        amountAdmin.setText(DecimalFormatter.roundNumber(accountService.findAccountByEmailUser(EMAIL_ADMIN).getBalance())
                + " "+ accountService.findAccountByEmailUser(EMAIL_ADMIN).getCurrency().getCurrencyCode());
        totalCommissionsLabel.setText(DecimalFormatter.roundNumber(feeService.getTotalFees()) + " $");
        totalTransactionsLabel.setText(transactionService.getTotalTransactions());
        totalUsersLabel.setText(accountService.getTotalUsers());
    }

    private void updateLoadPieChart() {
        Map<String, Long> transactionsByCurrency = transactionService.getTransactionCountByCurrency();
        categoryPieChart.getData().clear();
        for (Map.Entry<String, Long> entry : transactionsByCurrency.entrySet()) {
            categoryPieChart.getData().add(new PieChart.Data("Currency: " + entry.getKey(), entry.getValue()));
        }
    }



    private void updateLoadLineChart() {
        Map<String, Double> transactionsByDate = transactionService.getTransactionAmountsByDate();
        transactionsLineChart.getData().clear();
        XYChart.Series<String, Double> lines = new XYChart.Series<>();
        lines.setName("Amount for each day");
        for (Map.Entry<String, Double> entry : transactionsByDate.entrySet()) {
            lines.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        transactionsLineChart.getData().add(lines);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateInfomation();
        updateLoadPieChart();
        updateLoadLineChart();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdDashboardController that = (AdDashboardController) o;
        return Objects.equals(accountService, that.accountService) && Objects.equals(feeService, that.feeService) && Objects.equals(transactionService, that.transactionService) && Objects.equals(amountAdmin, that.amountAdmin) && Objects.equals(categoryPieChart, that.categoryPieChart) && Objects.equals(totalCommissionsLabel, that.totalCommissionsLabel) && Objects.equals(totalTransactionsLabel, that.totalTransactionsLabel) && Objects.equals(totalUsersLabel, that.totalUsersLabel) && Objects.equals(transactionsLineChart, that.transactionsLineChart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountService, feeService, transactionService, amountAdmin, categoryPieChart, totalCommissionsLabel, totalTransactionsLabel, totalUsersLabel, transactionsLineChart);
    }
}