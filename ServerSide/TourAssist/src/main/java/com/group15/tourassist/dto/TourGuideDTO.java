package com.group15.tourassist.dto;

import com.group15.tourassist.entity.GuideMaster;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TourGuideDTO {

    private Long packageId;

    private Optional<GuideMaster> guideMaster;

    private Instant priceStartDate;

    private Instant priceExpiryDate;

    private Double price;

    private Boolean isCustomizable;

}
