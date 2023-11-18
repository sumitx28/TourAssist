package com.group15.tourassist.dto;

import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.DestinationMaster;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PackageDTO {
    private Long id;
    private String packageName;
    private AgentDetailsDTO agent;
    private Instant tripStartDate;
    private Instant tripEndDate;
    private SourceMasterDTO source;
    private DestinationMasterDTO destination;
    private Instant packageCreatedDate;
    private Boolean isCustomizable;
}
