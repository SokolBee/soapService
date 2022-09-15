package org.sokolov.soapService.services;

import org.sokolov.soapService.models.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {
    void delete(Role role);
    void delete(Collection<Role> roles);
    void deleteById(Long id);
    Role save(Role role);
    List<Role> save(Collection<Role> roles);
}
