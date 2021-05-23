package com.hanna.sapeha.app.repository.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryUtilTest {

    @Test
    void shouldGetStartPositionOfEntityForSecondPage() {
        int pageNumber = 2;
        int pageSize = 10;

        int resultStartPosition = RepositoryUtil.getStartPosition(pageNumber, pageSize);
        assertEquals(10, resultStartPosition);
    }

    @Test
    void shouldGetStartPositionOfEntityForFirstPage() {
        int pageNumber = 1;
        int pageSize = 10;

        int resultStartPosition = RepositoryUtil.getStartPosition(pageNumber, pageSize);
        assertEquals(0, resultStartPosition);
    }
}