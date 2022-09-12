package org.sokolov.soapService.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sokolov.soapService.models.Role;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.services.RoleService;
import org.sokolov.soapService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Component("dataPropagation")
public class Data {

    protected final Logger log = LoggerFactory.getLogger(Data.class);


    protected final UserValidator validator;

    protected final UserService userService;

    protected final RoleService roleService;

    protected final RandomGenerator randomGenerator;

    @Autowired
    public Data(UserValidator validator, UserService userService, RoleService roleService, RandomGenerator randomGenerator) {
        this.validator = validator;
        this.userService = userService;
        this.roleService = roleService;
        this.randomGenerator = randomGenerator;
    }

    public void propagate() {
        List<Role> roles = roleService.save(createRoles(10));

        roles.forEach(role -> log.info(role.toString()));

        List<User> users = createUsers(20);
        bound(users, roles);

        users.forEach(user -> log.info(user.toString()));

        users.stream()
                .map(validator::validateUser)
                .forEach(constraintViolations ->
                        constraintViolations.forEach(violation ->
                                log.info(violation.getMessage())
                        ));


        userService.save(users);
    }

    protected void bound(final List<User> users, final List<Role> roles) {
        users.forEach(user -> {
                    int size = randomGenerator.nextInt(1, 4);
                    randomGenerator.ints(size, 0, 10)
                            .boxed()
                            .forEach(index -> user.getRoleSet().add(roles.get(index)));

                }
        );
    }

    protected List<User> createUsers(final int amount) {
        return Stream.generate(() ->
                        new User(login(), name(), password()))
                .limit(amount)
                .toList();
    }

    protected List<Role> createRoles(final int amount) {
        return Stream.generate(() ->
                        new Role(role()))
                .limit(amount)
                .toList();
    }

    protected String generateString(final int length, final int origin, final int bound) {
        return randomGenerator.ints(origin, bound)
                .filter(value -> value > 96 | (value < 91 & value > 64) | value < 58)
                .limit(length)
                .boxed()
                .map(Character::toString)
                .reduce("", String::concat);
    }

    protected String login() {
        return generateString(10, 97, 123);
    }

    protected String name() {
        return generateString(6, 65, 123);
    }

    protected String role() {
        return generateString(10, 65, 123);
    }

    protected String password() {
        String password = generateString(12, 48, 123);
        while (!(Pattern.compile("(?=.*\\d)(?=.*[A-Z])")
                .matcher(password)
                .find())) {
            password = generateString(12, 48, 123);
        }
        return password;
    }
}
