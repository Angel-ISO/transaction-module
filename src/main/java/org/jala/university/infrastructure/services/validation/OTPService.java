package org.jala.university.infrastructure.services.validation;

import lombok.Getter;
import org.springframework.stereotype.Service;



@Getter
@Service
public class OTPService {

    private final String currentOtp;

    public OTPService() {
        this.currentOtp = generateOtp();
    }

    private String generateOtp() {
        return String.format("%06d", (int) (Math.random() * 1_000_000));
    }
}
