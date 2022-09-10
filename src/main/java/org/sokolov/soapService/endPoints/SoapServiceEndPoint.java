package org.sokolov.soapService.endPoints;

import org.sokolov.soapService.generated.*;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.services.UserService;
import org.sokolov.soapService.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.validation.ConstraintViolation;
import javax.xml.bind.JAXBElement;
import java.util.Set;

@Endpoint
public class SoapServiceEndPoint extends AbstractSoapServiceEndPoint {

    @Autowired
    public SoapServiceEndPoint(Converter<User, SoapUser> soapUserConverter, Converter<SoapUser, User> userConverter,
                               UserService userService, UserValidator userValidator, ObjectFactory objectFactory) {
        super(soapUserConverter, userConverter, userService, userValidator, objectFactory);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    @Override
    public JAXBElement<UserWrapper> getAllUsers() {
        UserWrapper response = userWrapperResp();
        userService.findAll().forEach(user ->
                response.getUser().add(soapUserConverter.convert(user)));
        return factory.createGetAllUsersResponse(response);
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByLoginWithRolesRequest")
    @ResponsePayload
    @Override
    public JAXBElement<UserWrapper> getUserByLoginWithRoles(
            @RequestPayload JAXBElement<LoginWrapper> request) {
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    @Override
    public JAXBElement<OperationSuccess> deleteUserRequest(
            @RequestPayload JAXBElement<LoginWrapper> request) {
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addNewUserRequest")
    @ResponsePayload
    @Override
    public JAXBElement<OperationSuccess> addNewUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    @Override
    public JAXBElement<OperationSuccess> updateUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
        OperationSuccess response = operationSuccessResp();
        SoapUser soapUser = request.getValue().getUser().get(0);
        if (soapUser != null) {
            User user = userConverter.convert(soapUser);
            Set<ConstraintViolation<User>> violations = userValidator.validateUser(user);
            if (violations.isEmpty()) {
                User result = userService.save(user);
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