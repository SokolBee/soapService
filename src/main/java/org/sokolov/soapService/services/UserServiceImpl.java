package org.sokolov.soapService.services;

import org.sokolov.soapService.models.User;
import org.sokolov.soapService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpaUserService")
@Transactional
public class UserServiceImpl implements UserService {

    protected UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<User> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByLoginWithRoles(String login) {
        return repository.findUserByLoginWithRoles(login);
    }


    @Override
    public boolean delete(User user) {
        if (existByLogin(user.getLogin())) {
            repository.delete(user);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean deleteByLogin(String login) {
        if (existByLogin(login)) {
            repository.deleteByLogin(login);
            return true;
        }else{
            return false;
        }
    }


    @Override
    public User save(User user) {
        if (!existByLogin(user.getLogin())){
            return repository.save(user);
        }
        return null;
    }

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
