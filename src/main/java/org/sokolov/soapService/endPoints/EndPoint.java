package org.sokolov.soapService.endPoints;


import org.sokolov.soapService.aspects.Loggable;
import org.sokolov.soapService.generated.LoginWrapper;
import org.sokolov.soapService.generated.OperationSuccess;
import org.sokolov.soapService.generated.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.bind.JAXBElement;

import static org.sokolov.soapService.endPoints.AbstractSoapServiceEndPoint.NAMESPACE_URI;

@Endpoint
public class EndPoint {

    protected final FacadeSoapServiceEndPoint facade;

    @Autowired
    public EndPoint(FacadeSoapServiceEndPoint facade) {
        this.facade = facade;
    }

    @Loggable
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public JAXBElement<UserWrapper> getAllUsers() {
        return facade.getAllUsers();
    }

    @Loggable
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByLoginWithRolesRequest")
    @ResponsePayload
    public JAXBElement<UserWrapper> getUserByLoginWithRoles(
            @RequestPayload JAXBElement<LoginWrapper> request) {
        return facade.getUserByLoginWithRoles(request);
    }

    @Loggable
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public JAXBElement<OperationSuccess> deleteUserRequest(
            @RequestPayload JAXBElement<LoginWrapper> request) {
        return facade.deleteUserRequest(request);
    }

    @Loggable
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addNewUserRequest")
    @ResponsePayload

    public JAXBElement<OperationSuccess> addNewUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
        return facade.addNewUser(request);
    }

    @Loggable
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public JAXBElement<OperationSuccess> updateUser(
            @RequestPayload JAXBElement<UserWrapper> request) {
       return facade.updateUser(request);
    }





















}