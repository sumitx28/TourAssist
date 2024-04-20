package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ActivityRequest {

    private Long activityMasterId;

    private Instant activityDate;

    private Double price;

    private Boolean isCustomizable;

}
