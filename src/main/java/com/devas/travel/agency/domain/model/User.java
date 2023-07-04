package com.devas.travel.agency.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "user")
    private String userLogin;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "active")
    private boolean active;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId", referencedColumnName = "role_id", nullable = false)
    private Role role;

}
