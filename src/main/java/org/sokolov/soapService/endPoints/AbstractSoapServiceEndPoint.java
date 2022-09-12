package org.sokolov.soapService.endPoints;

import org.sokolov.soapService.generated.*;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.services.UserService;
import org.sokolov.soapService.utils.UserValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;



import javax.xml.bind.JAXBElement;

public abstract class AbstractSoapServiceEndPoint implements
        SWService<JAXBElement<UserWrapper>,UserWrapper, JAXBElement<LoginWrapper>,LoginWrapper,
                JAXBElement<OperationSuccess>,OperationSuccess>{
    protected static final String NAMESPACE_URI = "http://soap/users";

    protected final Converter<User,
            SoapUser> soapUserConverter;

    protected final Converter<SoapUser,
            User> userConverter;
    protected final UserService userService;

    protected final UserValidator userValidator;

    protected final ObjectFactory factory;

    public AbstractSoapServiceEndPoint(Converter<User, SoapUser> soapUserConverter, Converter<SoapUser, User> userConverter,
                                       UserService userService, UserValidator userValidator, ObjectFactory objectFactory) {
        this.soapUserConverter = soapUserConverter;
        this.userConverter = userConverter;
        this.userService = userService;
        this.userValidator = userValidator;
        this.factory = objectFactory;
    }

    public UserWrapper userWrapperResp() {
        return factory.createUserWrapper();
    }

    public OperationSuccess operationSuccessResp(){
        return factory.createOperationSuccess();
    }

}
