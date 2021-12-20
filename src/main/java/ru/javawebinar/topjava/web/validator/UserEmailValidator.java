package ru.javawebinar.topjava.web.validator;

import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UserEmailValidator implements ConstraintValidator<CheckDublicateEmail, User> {

    private final UserRepository repository;

    public UserEmailValidator(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void initialize(CheckDublicateEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = true;
        String email = user.getEmail().toLowerCase();
        User fromDb = repository.getByEmail(email);
        if (email.equals(fromDb.getEmail().toLowerCase()))
            return false;
        return result;
    }
}
