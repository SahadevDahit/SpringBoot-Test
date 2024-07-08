package com.example.test.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Table(name="users")
public class UserEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String username;

    private String password;

    @JsonIgnoreProperties(value = "users")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
            joinColumns=@JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns=@JoinColumn(name="role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles = new HashSet<>();


}
