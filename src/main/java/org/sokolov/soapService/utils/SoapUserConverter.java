package org.sokolov.soapService.utils;


import org.sokolov.soapService.generated.SoapRole;
import org.sokolov.soapService.generated.SoapUser;

import org.sokolov.soapService.models.Role;
import org.sokolov.soapService.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.persistence.PersistenceUnitUtil;
import java.util.Collection;

@Component("soapUserConverter")
public class SoapUserConverter implements Converter<User, SoapUser> {

    protected final PersistenceUnitUtil util;
    @Qualifier("soapRoleConverter")
    protected final Converter<Collection<Role>, Collection<SoapRole>> soapRoleConverter;

    @Autowired
    public SoapUserConverter(PersistenceUnitUtil util, Converter<Collection<Role>, Collection<SoapRole>> soapRoleConverter) {
        this.util = util;
        this.soapRoleConverter = soapRoleConverter;
    }

    @Override
    public SoapUser convert(@Nullable User source) {
        if (source == null) throw new IllegalArgumentException("User should be not null");
        SoapUser soapUser = new SoapUser();
        soapUser.setLogin(source.getLogin());
        soapUser.setName(source.getName());
        soapUser.setPassword(source.getPassword());

        if (util.isLoaded(source, "roleSet"))
            soapUser.getRoles().addAll(soapRoleConverter.convert(source.getRoleSet()));

        return soapUser;
    }
}
