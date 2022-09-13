package org.sokolov.soapService.services;

import org.sokolov.soapService.aspects.Loggable;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("jpaUserService")
@Transactional
public class UserServiceImpl implements UserService {

    protected UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }
    @Loggable
    @Transactional(readOnly = true)
    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }
    @Loggable
    @Transactional(readOnly = true)
    @Override
    public User findByLogin(String login) {
        return repository.findUserByLogin(login);
    }
    @Loggable
    @Transactional(readOnly = true)
    @Override
    public User findByLoginWithRoles(String login) {
        return repository.findUserByLoginWithRoles(login);
    }

    @Loggable
    @Override
    public boolean delete(User user) {
        if (existByLogin(user.getLogin())) {
            repository.delete(user);
            return true;
        }else{
            return false;
        }
    }
    @Loggable
    @Override
    public boolean deleteByLogin(String login) {
        if (existByLogin(login)) {
            repository.deleteByLogin(login);
            return true;
        }else{
            return false;
        }
    }

    @Loggable
    @Override
    public User save(User user) {
        if (!existByLogin(user.getLogin())){
            return repository.save(user);
        }
        return null;
    }
    @Loggable
    @Override
    public List<User> save(Collection<User> users) {
        List<User> result = new ArrayList<>();
        repository.saveAll(users).forEach(result::add);
        return result;
    }
    @Loggable
    @Override
    public User update(User user) {
        if (existByLogin(user.getLogin())){
            return repository.save(user);
        }
        return null;
    }

    @Override
    public boolean existByLogin(String login) {
        return repository.existsById(login);
    }
}
