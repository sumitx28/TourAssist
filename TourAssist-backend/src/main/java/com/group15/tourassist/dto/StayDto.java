package com.group15.tourassist.dto;

import com.group15.tourassist.entity.ResortMaster;
import com.group15.tourassist.entity.SuiteMaster;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StayDto {
    private Long id;
    private Long packageId;
    private Optional<ResortMaster> resortMaster;
    private Optional<SuiteMaster> suiteMaster;
    private Instant priceStartDate;
    private Instant priceExpiryDate;
    private Double price;
    private Boolean isCustomizable;
}
