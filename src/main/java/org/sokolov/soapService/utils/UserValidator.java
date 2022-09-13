package org.sokolov.soapService.utils;


import org.sokolov.soapService.aspects.Loggable;
import org.sokolov.soapService.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service("userValidator")
public class UserValidator {
    private final Validator validator;

    @Autowired
    public UserValidator(Validator validator) {
        this.validator = validator;
    }
    @Loggable
    public Set<ConstraintViolation<User>> validateUser(User user){
        return validator.validate(user);
    }
}
