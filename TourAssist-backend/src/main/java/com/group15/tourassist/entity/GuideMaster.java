package com.group15.tourassist.entity;

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
@Table(name = "guide_master")
public class GuideMaster implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guide_name")
    private String guideName;

    @Column(name = "experience_years")
    private String experienceYears;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "destination_master_id")
    private DestinationMaster destinationMaster;
}
