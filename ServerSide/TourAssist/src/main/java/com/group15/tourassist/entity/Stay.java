package com.group15.tourassist.entity;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.request.StayRequest;
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
@Table(name = "stay")
public class Stay implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "resort_master_id")
    private Long resortMasterId;

    @Column(name = "suite_master_id")
    private Long suiteMasterId;

    @Column(name = "price_start_date")
    private Instant priceStartDate;

    @Column(name = "price_expiry_date")
    private Instant priceExpiryDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_customizable")
    private Boolean isCustomizable;

    public static Stay createStayFromRequest(StayRequest request, Long packageId) {
        Stay stay = new Stay();
        stay.setPriceStartDate(Instant.now());
        stay.setPriceExpiryDate(Utils.getEndOfTime(Instant.now()));
        stay.setPrice(request.getPrice());
        stay.setResortMasterId(request.getResortId());
        stay.setSuiteMasterId(request.getSuiteId());
        stay.setIsCustomizable(request.getIsCustomizable());
        stay.setPackageId(packageId);

        return stay;
    }
}
