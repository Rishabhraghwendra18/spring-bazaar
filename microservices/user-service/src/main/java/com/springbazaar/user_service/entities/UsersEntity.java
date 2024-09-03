package com.springbazaar.user_service.entities;

import com.springbazaar.user_service.utils.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

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
    @NotNull
    private String email;
    @NotNull
    @Column(name = "phoneNo")
    private String phoneNo;
    @NotNull
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;
}

