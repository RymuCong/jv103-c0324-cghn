package com.cg.casestudy.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String message() default "Người dùng phải trên 17 tuổi";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}