package com.springbazaar.server.entities;

import com.springbazaar.server.utils.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
public class UsersEntity {
    @Column(name = "name")
    private String name;
    @Id
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNo")
    private String phoneNo;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
