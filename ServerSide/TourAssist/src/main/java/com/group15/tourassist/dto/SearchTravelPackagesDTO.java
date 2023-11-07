package com.group15.tourassist.dto;

import com.group15.tourassist.entity.PackageReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchTravelPackagesDTO {

    private Long packageId;
    private String packageName;
    private Double totalPackagePrice;
    private Instant tripStartDate;
    private Instant tripEndDate;
    private Instant packageCreatedDate;
    private Boolean isPackageCustomizable;
    private AgentDetailsDTO agentDetails;
    private List<PackageReview> packageReview;

}
