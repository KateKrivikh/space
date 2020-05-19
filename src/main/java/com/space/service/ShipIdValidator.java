package com.space.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ShipIdValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        long id = (Long) target;

        if (id < 0)
            errors.reject("value.negative");
        else if (id == 0)
            errors.reject("value.is0");
    }
}
