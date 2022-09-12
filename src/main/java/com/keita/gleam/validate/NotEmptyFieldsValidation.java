package com.keita.gleam.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class NotEmptyFieldsValidation implements ConstraintValidator<NotEmptyFields, List<String>> {
    @Override
    public void initialize(NotEmptyFields constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<String> objects, ConstraintValidatorContext constraintValidatorContext) {
        return objects.stream().allMatch(field -> field != null && !field.trim().isEmpty());
    }
}
