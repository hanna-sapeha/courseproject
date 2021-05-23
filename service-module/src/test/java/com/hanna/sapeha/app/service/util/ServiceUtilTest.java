package com.hanna.sapeha.app.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUtilTest {

    @Test
    void shouldGetRightNumbersOfPages() {
        int pageSize = 10;
        Long countEntities = 14L;
        var result = ServiceUtil.getNumbersOfPages(pageSize, countEntities);
        assertEquals(2, result);
    }

    @Test
    void shouldGetOnePageFor10Entity() {
        int pageSize = 10;
        Long countEntities = 10L;
        var result = ServiceUtil.getNumbersOfPages(pageSize, countEntities);
        assertEquals(1, result);
    }
}