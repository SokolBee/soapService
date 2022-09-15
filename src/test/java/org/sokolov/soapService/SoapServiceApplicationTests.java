package org.sokolov.soapService;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sokolov.soapService.config.AppConfig;
import org.sokolov.soapService.config.DSTestConfig;
import org.sokolov.soapService.config.JpaConfig;
import org.sokolov.soapService.models.Role;
import org.sokolov.soapService.models.User;
import org.sokolov.soapService.services.RoleService;
import org.sokolov.soapService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest(classes = {AppConfig.class, JpaConfig.class, DSTestConfig.class})
@ActiveProfiles("test")
class SoapServiceApplicationTests {

    private final Logger log = LoggerFactory.getLogger(SoapServiceApplicationTests.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private User expected;

    private Set<Role> roles;

    @BeforeEach
    void initTestData() {
        expected = new User();
        expected.setLogin("Waumok");
        expected.setName("Name");
        expected.setPassword("123456QW");
        roles = new HashSet<>() {{
            add(new Role("chief"));
            add(new Role("manager"));
        }};

        expected.setRoleSet(roles);

        expected = userService.save(expected);

        roles = expected.getRoleSet();
    }

    @AfterEach
    void destroyTestData() {
        userService.delete(expected);
        roleService.delete(roles);
    }

    @Test
    @DisplayName("USFindAll")
    void USFindAll() {
        List<User> users = new ArrayList<>();
        userService.findAll().forEach(users::add);
        users.forEach(actual -> assertEquals(expected, actual));
    }

    @Test
    @DisplayName("USFindByLogin")
    void USFindByLogin() {
        User actual = userService.findByLogin("Waumok");
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("USFindByLoginWithRoles")
    void USFindByLoginWithRoles() {
        User actual = userService.findByLoginWithRoles(expected.getLogin());
        assertEquals(expected.getRoleSet().size(), actual.getRoleSet().size());
        assertTrue(actual.getRoleSet().containsAll(expected.getRoleSet()));
    }

    @Test
    @DisplayName("deleteUser")
    void deleteUser() {
        userService.delete(expected);
        assertNull(userService.findByLogin(expected.getLogin()));
    }

    @Test
    @DisplayName("deleteByLogin")
    void deleteUserByLogin() {
        userService.deleteByLogin(expected.getLogin());
        assertNull(userService.findByLogin(expected.getLogin()));
    }

    @Test
    @DisplayName("saveUser")
    @Transactional
    void saveUser() {
        User user = new User();
        user.setLogin("TestUser");
        user.setName("UserName");
        user.setPassword("Password007");
        Role role = new Role("testRole");
        user.setRoleSet(new HashSet<>() {{
            add(role);
        }});

        userService.save(user);

        User actual = userService.findByLogin(user.getLogin());

        assertNotNull(actual);
        assertEquals(user, actual);
        assertEquals(user.getRoleSet().size(), actual.getRoleSet().size());
    }

    @Test
    @DisplayName("saveBatchOfUsers")
    @Transactional
    void saveBatchOfUsers() {
        User user0 = new User();
        User user1 = new User();
        user0.setLogin("TestUser_1");
        user1.setLogin("TestUser_2");
        user0.setName("UserName_1");
        user1.setName("UserName_2");
        user0.setPassword("Password007");
        user1.setPassword("Password004");
        Role role0 = new Role("testRole_0");
        Role role1 = new Role("testRole_1");
        Role role2 = new Role("testRole_2");
        user0.setRoleSet(new HashSet<>() {{
            add(role0);
        }});
        user1.setRoleSet(new HashSet<>() {{
            add(role1);
            add(role2);
        }});

        userService.save(user0);
        userService.save(user1);

        List<User> users = new ArrayList<>();
        userService.findAll()
                .forEach(users::add);
        assertEquals(users.size(), 3);

        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(expected);
        expectedUserList.add(user0);
        expectedUserList.add(user1);
        assertTrue(users.containsAll(expectedUserList));

        users.stream()
                .map(user -> user.getRoleSet().size())
                .reduce(Integer::sum)
                .ifPresentOrElse(sum ->
                                assertEquals(5, sum),
                        Assertions::fail);

    }
    @Test
    @DisplayName("updateUser")
    @Transactional
    void updateUser(){
        expected.setName("TESTUPDATE");
        expected.getRoleSet().add(new Role("TestUpdate"));
        userService.update(expected);

        User actual = userService.findByLogin("Waumok");

        assertEquals(expected.getName(),actual.getName());
        assertTrue(actual.getRoleSet().containsAll(expected.getRoleSet()));
    }

    @Test
    @DisplayName("existByLogin")
    void existByLogin(){
        assertTrue(userService.existByLogin("Waumok"));
    }



}
