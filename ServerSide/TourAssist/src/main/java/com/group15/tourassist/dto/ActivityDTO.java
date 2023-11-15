package com.group15.tourassist.dto;

import com.group15.tourassist.entity.ActivityMaster;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ActivityDTO {
    private Long packageId;
    private Optional<ActivityMaster> activityMaster;
    private Instant activityDate;
    // private Instant priceStartDate;
    //private Instant priceExpiryDate;
    private Double price;
    private Boolean isCustomizable;

}
