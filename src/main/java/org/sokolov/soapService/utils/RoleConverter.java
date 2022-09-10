package org.sokolov.soapService.utils;

import org.sokolov.soapService.generated.SoapRole;
import org.sokolov.soapService.models.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;


@Component("roleConverter")
public class RoleConverter implements Converter<Collection<SoapRole>, Collection<Role>> {
    @Override
    public Collection<Role> convert(@Nullable Collection<SoapRole> source) {
        if (source == null) throw new IllegalArgumentException("Role should be not null");
        return source.stream()
                .filter(Objects::nonNull)
                .map(RoleConverter::convertOne)
                .toList();
    }
    protected static Role convertOne(SoapRole soapRole){
        return new Role(soapRole.getId(), soapRole.getName());
    }
}
