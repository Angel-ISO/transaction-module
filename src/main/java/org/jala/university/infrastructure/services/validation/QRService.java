package org.jala.university.infrastructure.services.validation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QRService {

    private final RestTemplate restTemplate;

    public QRService() {
        this.restTemplate = new RestTemplate();
    }

    public byte[] generateQRCode(String data, String size) {
        String url = String.format("https://api.qrserver.com/v1/create-qr-code/?size=%s&data=%s", size, data);
        return restTemplate.getForObject(url, byte[].class);
    }
}
