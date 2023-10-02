package com.group15.tourassist.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "agent")
public class Agent {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private AppUser appUser;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "mobile")
    private Long mobile;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "verification_id")
    private String verificationId;

    @Column(name = "verification_doc_link")
    private String verificationDocLink;

}
