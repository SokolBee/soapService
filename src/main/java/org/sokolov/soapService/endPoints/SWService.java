package org.sokolov.soapService.endPoints;

import javax.xml.bind.JAXBElement;

public interface SWService<A extends JAXBElement<U>, U,
        B extends JAXBElement<L>, L,
        C extends JAXBElement<O>, O> {

    A getAllUsers();

    A getUserByLoginWithRoles(B b);

    C deleteUserRequest(B b);

    C addNewUser(A a);

    C updateUser(A a);
}
