package net.IFTS11.maquina_Express.maquina_Express.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.IFTS11.maquina_Express.maquina_Express.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExistbyUsernameValidator implements ConstraintValidator<ExistsbyUsername,String> {

    @Autowired
    private UserService service;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !service.existsByUsername(username);
    }
}
