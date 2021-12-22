package ru.javawebinar.topjava.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

//@Component
//public class UserValidator implements Validator {
//
//    private final UserRepository repository;
//
//    public UserValidator(UserRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return User.class.isAssignableFrom(clazz);
//    }
//
//    @Override
//    public void validate(Object target, Errors errors) {
//        ValidationUtils.rejectIfEmpty(errors, "email",
//                "email.dublicate");
//        User user = (User) target;
//        User userFromRepo = repository.getByEmail(user.getEmail().toLowerCase());
//        if (!(userFromRepo != null && userFromRepo.getEmail().equals(((User) target).getEmail()))) {
//            errors.rejectValue("Email error", "value must be unique");
//        }
//    }
//}
