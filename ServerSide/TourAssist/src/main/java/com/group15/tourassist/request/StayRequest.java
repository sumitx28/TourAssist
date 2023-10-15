package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StayRequest {

    private Long resortId;

    private Long suiteId;

    private Double price;

    private Boolean isCustomizable;
}
