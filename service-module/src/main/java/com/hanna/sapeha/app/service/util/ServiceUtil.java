package com.hanna.sapeha.app.service.util;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtil {

    public static int getNumbersOfPages(int pageSize, Long countEntities) {
        return (int) Math.ceil((double) countEntities / pageSize);
    }
}
