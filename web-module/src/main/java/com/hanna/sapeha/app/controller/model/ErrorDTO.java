package com.hanna.sapeha.app.controller.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorDTO {

    private List<String> errors = new ArrayList<>();

    public void add(String errorMessage) {
        errors.add(errorMessage);
    }
}
