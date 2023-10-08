/*
package com.group15.tourassist.domain;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AppUser appUser;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile")
    private Long mobile;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "country")
    private String country;

}
*/
