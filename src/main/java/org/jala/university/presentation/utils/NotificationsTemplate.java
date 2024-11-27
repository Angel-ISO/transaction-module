package org.jala.university.presentation.utils;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jala.university.presentation.auth.UserSession;

public class NotificationsTemplate {

    private NotificationsTemplate(){}

    /**
     * Crea un nodo para mostrar una notificación.
     *
     * @param time          Hora de la notificación.
     * @param username      Nombre del usuario logueado.
     * @param amount        Monto de la transacción.
     * @param currency      Moneda de la transacción.
     * @param counterparty  Nombre del remitente o destinatario.
     * @param isSender      True si el usuario es el remitente; false si es el destinatario.
     * @return Nodo VBox estilizado.
     */

    public static VBox createNotification(String time, String username, String amount, String currency, String counterparty, boolean isSender) {
        VBox notificationBox = new VBox();
        notificationBox.getStyleClass().add("notification");
        notificationBox.getStyleClass().add(isSender ? "sent" : "received");

        Text timeText = new Text(time);
        timeText.getStyleClass().add("time");

        TextFlow messageFlow = new TextFlow();
        messageFlow.getStyleClass().add("message");

        String loggedInUser = UserSession.getInstance().getLoggedUser().getUsername();

        if (isSender) {
            messageFlow.getChildren().addAll(
                    new Text("Hey "),
                    createBoldText(loggedInUser),
                    new Text(",\n"),
                    new Text("your deposit was successfully sent to ")
            );
        } else {
            messageFlow.getChildren().addAll(
                    new Text("Hey "),
                    createBoldText(loggedInUser),
                    new Text(",\n"),
                    new Text("you received a deposit of ")
            );
        }

        HBox amountCurrencyBox = new HBox();
        amountCurrencyBox.setSpacing(5);
        amountCurrencyBox.setStyle("-fx-alignment: center-left;");

        Text amountText = new Text(amount);
        amountText.getStyleClass().add("amount");

        Text currencyText = new Text(currency);
        currencyText.getStyleClass().add("currency");

        amountCurrencyBox.getChildren().addAll(amountText, currencyText);


        TextFlow counterpartyFlow = new TextFlow();
        counterpartyFlow.getStyleClass().add(isSender ? "to" : "from");
        counterpartyFlow.getChildren().addAll(
                new Text(isSender ? "to " : "from "),
                createBoldText(counterparty)
        );

        notificationBox.getChildren().addAll(timeText, messageFlow, amountCurrencyBox, counterpartyFlow);

        return notificationBox;
    }


    private static Text createBoldText(String text) {
        Text boldText = new Text(text);
        boldText.getStyleClass().add("username");
        return boldText;
    }
}
