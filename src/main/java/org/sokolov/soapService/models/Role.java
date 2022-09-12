package org.sokolov.soapService.models;
import org.hibernate.annotations.Immutable;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Immutable
@Table(name = "ROLES")
public class Role implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false, updatable = false, unique = true)
    protected String name;
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role() {
    }
    public Role(String name) {
        this.name = name;
    }
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
