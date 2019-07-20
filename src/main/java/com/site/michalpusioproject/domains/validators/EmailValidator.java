package com.site.michalpusioproject.domains.validators;

import com.site.michalpusioproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidIsExistEmail, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(ValidIsExistEmail constraintAnnotation) {
    }
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context){
        return (validateEmail(email));
    }
    private boolean validateEmail(String email) {
        return userService.isEmailExistInDatabase(email);
    }
}