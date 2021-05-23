package com.hanna.sapeha.app.controller;

import com.hanna.sapeha.app.config.BaseIT;
import com.hanna.sapeha.app.service.model.ItemDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.hanna.sapeha.app.constant.HandlerConstants.API_URL;
import static com.hanna.sapeha.app.constant.HandlerConstants.ITEMS_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
class ItemAPIControllerIT extends BaseIT {

    @Test
    @Sql({"/scripts/clean_item.sql", "/scripts/init_item.sql"})
    void shouldGetAllItems() throws Exception {
        HttpEntity<String> request = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<List<ItemDTO>> response = testRestTemplate.exchange(
                API_URL + ITEMS_URL,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("test", response.getBody().get(0).getName());
        assertEquals("summary", response.getBody().get(0).getSummary());
        Assertions.assertNotNull(response.getBody().get(0).getUniqueNumber());
    }
}