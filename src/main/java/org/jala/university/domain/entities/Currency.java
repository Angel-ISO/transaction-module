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
@Table(name = "currencies")
public class Currency extends BaseEntity {
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @Column(name = "currency_name", nullable = false, length = 50)
    private String currencyName;

    @Column(name = "exchange_rate", nullable = false)
    private double exchangeRate;

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
        Currency currency = (Currency) o;
        return Double.compare(currency.exchangeRate, exchangeRate) == 0 &&
                Objects.equals(getId(), currency.getId()) &&
                Objects.equals(currencyCode, currency.currencyCode) &&
                Objects.equals(currencyName, currency.currencyName) &&
                Objects.equals(createdAt, currency.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), currencyCode, currencyName, exchangeRate, createdAt);
    }
}
