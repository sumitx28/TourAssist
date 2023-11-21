package com.group15.tourassist.entity;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.request.TourGuideRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tour_guide")
public class TourGuide implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "guide_master_id")
    private Long guideMasterId;

    @Column(name = "price_start_date")
    private Instant priceStartDate;

    @Column(name = "price_expiry_date")
    private Instant priceExpiryDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_customizable")
    private Boolean isCustomizable;

    public static TourGuide createTourGuide(TourGuideRequest request, Long packageId) {
        TourGuide tourGuide = new TourGuide();
        tourGuide.setGuideMasterId(request.getGuideId());
        tourGuide.setPriceStartDate(Instant.now());
        tourGuide.setPriceExpiryDate(Utils.getEndOfTime(Instant.now()));
        tourGuide.setPrice(request.getPrice());
        tourGuide.setIsCustomizable(request.getIsCustomizable());
        tourGuide.setPackageId(packageId);


        return tourGuide;
    }
}
