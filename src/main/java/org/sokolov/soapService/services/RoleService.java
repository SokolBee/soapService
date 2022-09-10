package org.sokolov.soapService.services;

import org.sokolov.soapService.models.Role;

public interface RoleService {
    void delete(Role role);
    void deleteById(Long id);
    Role save(Role role);
}
