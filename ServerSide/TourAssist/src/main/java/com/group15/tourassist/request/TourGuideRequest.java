package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TourGuideRequest {

    private Long guideId;

    private Long price;

    private Boolean isCustomizable;

}
