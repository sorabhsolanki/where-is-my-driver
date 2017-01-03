package com.locate.driver.valid;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

public class ValidationMessage {

    private final List<String> errors = new ArrayList<>();

    public ValidationMessage() {
    }

    public List<String> getErrors() {
        return errors;
    }
}
