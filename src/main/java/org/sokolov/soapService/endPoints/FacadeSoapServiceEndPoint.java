package org.sokolov.soapService.endPoints;

import org.sokolov.soapService.aspects.Loggable;
import org.sokolov.soapService.generated.*;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.services.UserService;
import org.sokolov.soapService.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBElement;
import java.util.Set;

@Service
public class FacadeSoapServiceEndPoint extends AbstractSoapServiceEndPoint {

    @Autowired
    public FacadeSoapServiceEndPoint(@Qualifier("soapUserConverter") Converter<User, SoapUser> soapUserConverter,
                                     @Qualifier("userConverter") Converter<SoapUser, User> userConverter,
                                     UserService userService, UserValidator userValidator, ObjectFactory objectFactory) {
        super(soapUserConverter, userConverter, userService, userValidator, objectFactory);
    }
    @Override
    public JAXBElement<UserWrapper> getAllUsers() {
        UserWrapper response = userWrapperResp();
        userService.findAll().forEach(user ->
                response.getUser().add(soapUserConverter.convert(user)));
        return factory.createGetAllUsersResponse(response);
    }

    @Override
    public JAXBElement<UserWrapper> getUserByLoginWithRoles(
            JAXBElement<LoginWrapper> request) {
        UserWrapper response = userWrapperResp();
        String login = request.getValue().getLogin().get(0);
        if (login != null) {
            response.getUser().add(soapUserConverter.convert(
                    userService.findByLoginWithRoles(login)));
        } else {
            throw new RuntimeException("Login should not be null");
        }
        return factory.createGetUserByLoginWithRolesResponse(response);
    }

    @Override
    public JAXBElement<OperationSuccess> deleteUserRequest(
            JAXBElement<LoginWrapper> request) {
        OperationSuccess response = operationSuccessResp();
        String login = request.getValue().getLogin().get(0);
        if (login != null) {
            boolean result = userService.deleteByLogin(login);
            if (result) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.getErrors().add("User with login \"" + login + "\" doesn't exist");
            }
        }
        return factory.createDeleteUserResponse(response);
    }

    @Override
    public JAXBElement<OperationSuccess> addNewUser(
            JAXBElement<UserWrapper> request) {
        OperationSuccess response = operationSuccessResp();
        SoapUser soapUser = request.getValue().getUser().get(0);
        if (soapUser != null) {
            User user = userConverter.convert(soapUser);
            Set<ConstraintViolation<User>> violations = userValidator.validateUser(user);
            if (violations.isEmpty()) {
                userService.save(user);
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                violations.forEach(err ->
                        response.getErrors().add(err.getMessage()));
            }
        }
        return factory.createAddNewUserResponse(response);
    }

    @Override
    public JAXBElement<OperationSuccess> updateUser(
            JAXBElement<UserWrapper> request) {
        OperationSuccess response = operationSuccessResp();
        SoapUser soapUser = request.getValue().getUser().get(0);
        if (soapUser != null) {
            User user = userConverter.convert(soapUser);
            Set<ConstraintViolation<User>> violations = userValidator.validateUser(user);
            if (violations.isEmpty()) {
                User result = userService.update(user);
                if (result != null) {
                    response.setSuccess(true);
                    return factory.createUpdateUserResponse(response);
                }
            }
            response.setSuccess(false);
            violations.forEach(err ->
                    response.getErrors().add(err.getMessage()));
        }
        return factory.createUpdateUserResponse(response);
    }
}