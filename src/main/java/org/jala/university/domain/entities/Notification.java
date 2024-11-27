package org.jala.university.domain.entities;


import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String sourceAccountId;
    private String destinationAccountId;
    private double amount;
    private LocalDateTime timestamp;

    public Notification(String sourceAccountId, String destinationAccountId, double amount) {
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
    }

}
