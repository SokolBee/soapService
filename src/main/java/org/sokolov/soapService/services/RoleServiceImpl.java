package org.sokolov.soapService.services;

import org.sokolov.soapService.models.Role;
import org.sokolov.soapService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaRoleService")
@Transactional
public class RoleServiceImpl implements RoleService{

    protected RoleRepository repository;

    @Autowired
    public RoleServiceImpl(RoleRepository repository){
        this.repository = repository;
    }

    @Override
    public void delete(Role role) {
        repository.delete(role);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }
}
