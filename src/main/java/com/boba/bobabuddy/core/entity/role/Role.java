package com.boba.bobabuddy.core.entity.role;

import com.boba.bobabuddy.core.entity.User;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {
    @Id @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {
    }
}
