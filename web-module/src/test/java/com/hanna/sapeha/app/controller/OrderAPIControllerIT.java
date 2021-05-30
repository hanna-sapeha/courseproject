package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.config.BaseIT;
import com.hanna.sapeha.app.service.model.OrderDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ORDERS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
public class OrderAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_orders.sql", "/scripts/init_orders.sql"})
    void shouldGetAllOrders() {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<OrderDTO>> response = testRestTemplate.exchange(
                API_URL + ORDERS_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
//        assertEquals("NEW", response.getBody().get(0).getStatus());
//        assertEquals(1, Objects.requireNonNull(response.getBody().size()));
//        assertEquals(1L, response.getBody().get(0).getNumberOfOrder());
    }
}
