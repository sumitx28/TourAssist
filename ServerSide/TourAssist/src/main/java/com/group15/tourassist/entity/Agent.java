package com.group15.tourassist.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.tourassist.request.AgentRegistrationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "agent")
public class Agent implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JsonIgnore
    private AppUser appUser;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "verification_id")
    private String verificationId;

    @Column(name = "verification_doc_link")
    private String verificationDocLink;

    public static Agent getAgentForRegister(AgentRegistrationRequest request, AppUser appUser) {
        Agent agent = new Agent();
        agent.setCompanyName(request.getCompanyName());
        agent.setEmployeeCount(request.getEmployeeCount());
        agent.setMobile(request.getMobile());
        agent.setVerificationId(request.getVerificationId());
        agent.setVerificationDocLink(request.getVerificationDocLink());
        agent.setAppUser(appUser);

        return agent;
    }
}
