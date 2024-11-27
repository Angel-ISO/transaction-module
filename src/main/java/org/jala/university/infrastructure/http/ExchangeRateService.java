package org.jala.university.infrastructure.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ExchangeRateService {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Value("${exchange.api.key}")
    private String apiKey;

    private static final String APIURL = "https://v6.exchangerate-api.com/v6/";

    public ExchangeRateResponse getExchangeRates(String baseCurrency) {
        String url = APIURL + apiKey + "/latest/" + baseCurrency;

        logger.info("URL construida: {}", url);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        try {
            return restTemplate.getForObject(url, ExchangeRateResponse.class);
        } catch (HttpClientErrorException e) {
            logger.error("Error en la llamada a la API de tasas de cambio: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Error al obtener las tasas de cambio. Detalles: " + e.getMessage(), e);
        } catch (ResourceAccessException e) {
            logger.error("Error de conexión a la API de tasas de cambio: {}", e.getMessage());
            throw new RuntimeException("Error al acceder a la API. Verifique la conexión.", e);
        } catch (Exception e) {
            logger.error("Error desconocido al llamar a la API: {}", e.getMessage());
            throw new RuntimeException("Error al obtener las tasas de cambio. Detalles: " + e.getMessage(), e);
        }
    }
}
