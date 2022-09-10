package org.sokolov.soapService.repositories;

import org.sokolov.soapService.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User,String> {
    User findUserByLogin(String login);

    @Query("select u from User u left join fetch u.roleSet where u.login = :login")
    User findUserByLoginWithRoles(@Param("login") String login);

    void deleteByLogin(String login);

}
