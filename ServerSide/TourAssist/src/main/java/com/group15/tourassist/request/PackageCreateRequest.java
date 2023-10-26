package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PackageCreateRequest {

    private String packageName;

    private Long agentId;

    private Instant tripStartDate;

    private Instant tripEndDate;

    private Long sourceId;

    private Long destinationId;

    private Boolean isCustomizable;

    private List<ActivityRequest> activities;

    private StayRequest stayRequest;

    private TourGuideRequest tourGuideRequest;

    private TransportationRequest transportationRequest;

    private List<PackageMediaRequest> packageMediaRequests;

}
