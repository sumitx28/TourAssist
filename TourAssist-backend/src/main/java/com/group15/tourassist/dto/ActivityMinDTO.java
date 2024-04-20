package com.group15.tourassist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Minimum DTO created for showing Activity details for booking details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityMinDTO {

    private Long activityId;
    private String activityName;

}
