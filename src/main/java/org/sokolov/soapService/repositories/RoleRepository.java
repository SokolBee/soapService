package org.sokolov.soapService.repositories;

import org.sokolov.soapService.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {
}
