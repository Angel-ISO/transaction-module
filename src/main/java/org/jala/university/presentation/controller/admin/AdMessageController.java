package org.jala.university.presentation.controller.admin;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.jala.university.domain.entities.Fee;
import org.jala.university.infrastructure.services.AccountService;
import org.jala.university.infrastructure.services.FeeService;
import org.jala.university.presentation.commons.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class AdMessageController extends BaseController implements Initializable {

    @FXML
    private VBox containerMessage;

    private final FeeService feeService;
    private final AccountService accountService;

    @Autowired
    public AdMessageController(FeeService feeService, AccountService accountService) {
        this.feeService = feeService;
        this.accountService = accountService;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        containerMessage.setSpacing(20);
        List<Fee> fees = feeService.getAll().stream()
                .filter(fee -> fee.getCreatedAt() != null)
                .filter(fee -> fee.getCreatedAt().isAfter(LocalDate.now().minusDays(7).atStartOfDay()))
                .sorted((fee1, fee2) -> fee2.getCreatedAt().compareTo(fee1.getCreatedAt()))
                .collect(Collectors.toList());
        loadFeeData(fees);
    }


    public void loadFeeData(List<Fee> fees) {
        containerMessage.getChildren().clear();
        for (Fee fee : fees) {
            if (fee != null) {
                containerMessage.getChildren().add(createFeeBox(fee));
            }
        }
    }

    private Pane createFeeBox(Fee fee) {
        Pane pane = new Pane();
        pane.getStyleClass().add("card");

        Label title = new Label(fee.getFeeName());
        title.getStyleClass().add("title");

        Label account = new Label("ID: " + accountService.findIdByFeeId(fee.getId()));
        account.getStyleClass().add("account");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm");
        Label dateLabel = new Label("Fecha de creación: " + fee.getCreatedAt().format(dateFormatter));
        dateLabel.getStyleClass().add("date");

        Label description = new Label(fee.getDescription());
        description.getStyleClass().add("description");

        Label mount = new Label("$" + fee.getAmount());
        mount.getStyleClass().add("amount");

        HBox titleBox = new HBox(10, title, account);
        titleBox.setSpacing(10);
        titleBox.getStyleClass().add("title-box");

        VBox content = new VBox(10, titleBox, dateLabel, description, mount);
        content.setPadding(new Insets(15));
        content.getStyleClass().add("content-box");

        pane.getChildren().add(content);

        return pane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AdMessageController that = (AdMessageController) o;
        return Objects.equals(containerMessage, that.containerMessage) && Objects.equals(feeService, that.feeService) && Objects.equals(accountService, that.accountService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), containerMessage, feeService, accountService);
    }
}
