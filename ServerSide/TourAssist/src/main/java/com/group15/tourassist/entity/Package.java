package com.group15.tourassist.entity;

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
@Table(name = "package")
public class Package implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_name")
    private String packageName;

    @Column(name = "agent_id")
    private Long agentId;

    @Column(name = "trip_start_date")
    private Instant tripStartDate;

    @Column(name = "trip_end_date")
    private Instant tripEndDate;

    @Column(name = "source_id")
    private Long sourceId;

    @Column(name = "destination_id")
    private Long destinationId;

    @Column(name = "package_created_date")
    private Instant packageCreatedDate;

    @Column(name = "is_customizable")
    private Boolean isCustomizable;

}
