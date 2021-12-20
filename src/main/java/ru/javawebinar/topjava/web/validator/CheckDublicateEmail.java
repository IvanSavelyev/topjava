package ru.javawebinar.topjava.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UserEmailValidator.class)
@Documented
public @interface CheckDublicateEmail {
    String message() default "User Email must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}