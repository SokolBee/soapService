package org.sokolov.soapService.services;

import org.sokolov.soapService.aspects.Loggable;
import org.sokolov.soapService.models.Role;
import org.sokolov.soapService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("jpaRoleService")
@Transactional
public class RoleServiceImpl implements RoleService {

    protected RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository) {
        this.repository = repository;
    }

    @Loggable
    @Override
    public void delete(Role role) {
        repository.delete(role);
    }

    @Loggable
    @Override
    public void delete(Collection<Role> roles) {
        repository.deleteAll(roles);
    }

    @Loggable
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Loggable
    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Loggable
    @Override
    public List<Role> save(Collection<Role> roles) {
        List<Role> result = new ArrayList<>();
        repository.saveAll(roles).forEach(result::add);
        return result;
    }
}
