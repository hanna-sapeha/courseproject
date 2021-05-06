package com.hanna.sapeha.app.repository.model.enums;

public enum RolesEnum {
    ADMINISTRATOR("Administrator"),
    SALE_USER("Sale User"),
    CUSTOMER_USER("Customer User"),
    SECURE_REST_API("Secure REST API");

    private final String description;

    RolesEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
