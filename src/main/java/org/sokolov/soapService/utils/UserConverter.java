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

import java.util.Collection;

@Component("userConverter")
public class UserConverter implements Converter<SoapUser, User> {
    @Qualifier("roleConverter")
    protected Converter<Collection<SoapRole>, Collection<Role>> roleConverter;

    @Autowired
    public UserConverter(Converter<Collection<SoapRole>, Collection<Role>>  roleConverter) {
        this.roleConverter = roleConverter;
    }

    @Override
    public User convert(@Nullable SoapUser source) {
        if (source == null) throw new IllegalArgumentException("SoapUser should be not null");
        User user = new User();
        user.setLogin(source.getLogin());
        user.setName(source.getName());
        user.setPassword(source.getPassword());

        if (source.getRoles() != null && source.getRoles().size() > 0) {
            user.getRoleSet().addAll(roleConverter.convert(source.getRoles()));
        }
        return user;
    }
}
