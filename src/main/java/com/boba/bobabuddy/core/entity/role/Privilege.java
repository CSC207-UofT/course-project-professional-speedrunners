package com.boba.bobabuddy.core.entity.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
public class Privilege {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "privileges")
    Collection<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
