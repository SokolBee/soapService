package org.sokolov.soapService.models;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Transient
    protected static final String pattern = "(.*[A-Z]+.*[a-z]*.*[\\d]+.*)|(.*[a-z]*.*[\\d]+.*[A-Z]+.*)|(.*[\\d]+.*[A-Z]+.*[a-z]*.*)|(.*[A-Z]+.*[\\d]+.*[a-z]*.*)|(.*[a-z]*.*[A-Z]+.*[\\d]+.*)|(.*[\\d]+.*[a-z]*.*[A-Z]+.*)";

    @Id
    @Column(name = "USER_LOGIN")
    @NotNull(message = "Login should not be null")
    protected String login;

    @NotEmpty(message = "Name should not be empty")
    @NotNull(message = "Name should not be null")
    @Column(name = "USER_NAME", nullable = false)
    protected String name;

    @NotNull(message = "Password should not be null")
    @Pattern(regexp = pattern, message = "Password should have at least one capital letter and one figure")
    @Column(name = "USER_PASSWORD", columnDefinition =
            "varchar(30) not null " +
                    " check (USER_PASSWORD ~ '(?=.*\\d)(?=.*[A-Z])')"
    )
    protected String password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected Set<Role> roleSet;

    public User() {
    }

    public User(String login, String name, String password, Role... roles) {
        this.login = login;
        this.name = name;
        this.password = password;
        this.roleSet = new HashSet<>(Arrays.asList(roles));
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleSet(Set<Role> roleSet) {
        this.roleSet = roleSet;
    }


    public Set<Role> getRoleSet() {
        return roleSet;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, name, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", roleSet=" + roleSet +
                '}';
    }
}
