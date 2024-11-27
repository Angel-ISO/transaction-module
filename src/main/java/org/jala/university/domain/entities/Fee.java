package org.jala.university.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;


@Data
@NoArgsConstructor
@Entity
@Table(name = "fees")
public class Fee extends BaseEntity {
    @Column(name = "fee_name", nullable = false, length = 50)
    private String feeName;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "applicable_to", nullable = false)
    @Enumerated(EnumType.STRING)
    private ApplicableTo applicableTo;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee = (Fee) o;
        return Double.compare(fee.amount, amount) == 0 &&
                Objects.equals(getId(), fee.getId()) &&
                Objects.equals(feeName, fee.feeName) &&
                applicableTo == fee.applicableTo &&
                Objects.equals(description, fee.description) &&
                Objects.equals(createdAt, fee.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), feeName, amount, applicableTo, description, createdAt);
    }

}

