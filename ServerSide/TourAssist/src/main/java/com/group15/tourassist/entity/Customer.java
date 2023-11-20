package com.group15.tourassist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.tourassist.request.CustomerRegistrationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "customer")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private AppUser appUser;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "country")
    private String country;


    public static Customer getCustomerForRegister(CustomerRegistrationRequest request, AppUser appUser) {
        Customer customer = new Customer();
        customer.setAppUser(appUser);
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDateOfBirth(request.getDateOfBirth());
        customer.setCountry(request.getCountry());
        customer.setMobile(request.getMobile());
        return customer;
    }
}

