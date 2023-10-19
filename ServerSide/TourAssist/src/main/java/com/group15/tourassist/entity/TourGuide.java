package com.group15.tourassist.entity;

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

}
