package com.group15.tourassist.domain;

import com.group15.tourassist.core.enumeration.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/*
-- This is credentials table which stores email and passwords of all the users.
-- user_type = {customer, agent, admin}
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "app_user")
public class AppUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
