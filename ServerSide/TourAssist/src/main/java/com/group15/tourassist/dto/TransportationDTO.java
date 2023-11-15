package com.group15.tourassist.dto;

import com.group15.tourassist.entity.TravelModeMaster;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransportationDTO {

    private Long packageId;

    private String mode;
    //private Optional<TravelModeMaster> modeMaster;

    // private Instant priceStartDate;

    //private Instant priceExpiryDate;

    private Double price;

    private Boolean isCustomizable;
}
