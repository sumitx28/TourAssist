package com.group15.tourassist.response;

import com.group15.tourassist.dto.*;
import com.group15.tourassist.entity.*;
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
public class PackageDetailResponse {
    private String packageName;
    private List<PackageMedia> mediaPath;
    private AgentDetailsDTO agentDetails;
    private SourceMasterDTO sourceDetails;
    private DestinationMasterDTO destinationDetails;
    private Instant tripStartDate;
    private Instant tripEndDate;
    private List<ActivityDTO> activity;
    private TransportationDTO transportationDetails;
    private StayDto stay;
    //private ResortMaster resorts;
    private TourGuideDTO tourGuide;
    private Boolean isCustomizable;
}

