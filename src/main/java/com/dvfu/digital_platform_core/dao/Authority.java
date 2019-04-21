package com.dvfu.digital_platform_core.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name="AUTHORITY")
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = -6173672801831223096L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated( EnumType.STRING)
    @Column(name="name")
    UserRoleName name;

    @Override
    public String getAuthority() {
        return name.name();
    }

    public void setName(UserRoleName name) {
        this.name = name;
    }


    public UserRoleName getName() {
        return name;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
