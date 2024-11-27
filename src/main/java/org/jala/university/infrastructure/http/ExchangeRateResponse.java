package org.jala.university.infrastructure.http;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Setter
@Getter
public class ExchangeRateResponse {
    private String base;
    private String date;

    @JsonProperty("conversion_rates")
    private Map<String, BigDecimal> rates;

}
