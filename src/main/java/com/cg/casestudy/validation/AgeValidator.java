package com.cg.casestudy.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.time.ZoneId;

public class AgeValidator implements ConstraintValidator<Age, Date> {

    @Override
    public void initialize(Age age) {
    }

    @Override
    public boolean isValid(Date dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return false;
        }
        LocalDate birthDate = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthDate, LocalDate.now()).getYears() > 17;
    }
}