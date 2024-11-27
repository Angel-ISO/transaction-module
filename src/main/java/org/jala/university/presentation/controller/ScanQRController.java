package org.jala.university.presentation.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jala.university.infrastructure.services.validation.OTPService;
import org.jala.university.infrastructure.services.validation.QRService;
import org.jala.university.presentation.auth.UserSession;
import org.jala.university.presentation.commons.BaseController;
import org.jala.university.presentation.utils.AlertMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class ScanQRController extends BaseController implements Initializable {

    @FXML
    private ImageView qrImageView;

    @FXML
    private TextField codeimput;



    private final OTPService otpService;

    private final QRService qrService;


    @Autowired
    public ScanQRController(OTPService otpService, QRService qrService) {
        this.otpService = otpService;
        this.qrService = qrService;
    }

    private void generateScanQR(String data) {
        byte[] qrData = qrService.generateQRCode(data, "250x250");

        Image qrImage = new Image(new java.io.ByteArrayInputStream(qrData));

        qrImageView.setImage(qrImage);
    }

    @FXML
    private void validateQRCode() {
        String inputCode = codeimput.getText().trim();

        if (inputCode.equals(otpService.getCurrentOtp())) {
            UserSession.getInstance().setOtpVerified(true);

            AlertMessage.showAlert("Great!", "the code is correct. Now u can send money!");
        } else {
            AlertMessage.showAlert("Error", "the code is not correct.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String otp = otpService.getCurrentOtp();
        generateScanQR(otp);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ScanQRController that = (ScanQRController) o;
        return Objects.equals(qrImageView, that.qrImageView) && Objects.equals(codeimput, that.codeimput) && Objects.equals(otpService, that.otpService) && Objects.equals(qrService, that.qrService);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), qrImageView, codeimput, otpService, qrService);
    }
}

