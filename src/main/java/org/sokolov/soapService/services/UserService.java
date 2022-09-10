package org.sokolov.soapService.services;

import org.sokolov.soapService.models.User;

import java.util.List;

public interface UserService {

    Iterable<User> findAll();
    User findByLogin(String login);
    User findByLoginWithRoles(String login);
    boolean delete(User user);

    boolean deleteByLogin(String login);
    User save(User user);

    User update(User user);

    boolean existByLogin(String login);

}
