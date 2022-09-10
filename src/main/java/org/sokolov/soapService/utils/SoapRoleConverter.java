package org.sokolov.soapService.utils;

import org.sokolov.soapService.generated.SoapRole;
import org.sokolov.soapService.models.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Component("soapRoleConverter")
public class SoapRoleConverter implements Converter<Collection<Role>, Collection<SoapRole>> {
    @Override
    public Collection<SoapRole> convert(@Nullable Collection<Role> source) {
        if (source == null) throw new IllegalArgumentException("Role should be not null");
        return source.stream()
                .filter(Objects::nonNull)
                .map(SoapRoleConverter::convertOne)
                .toList();
    }
    protected static SoapRole convertOne(Role role) {
        SoapRole soapRole = new SoapRole();
        soapRole.setId(role.getId());
        soapRole.setName(role.getName());
        return soapRole;
    }
}

