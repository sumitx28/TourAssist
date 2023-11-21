package com.group15.tourassist.entity;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.request.TransportationRequest;
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
@Table(name = "transportation")
public class Transportation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "mode_master_id")
    private Long modeMasterId;

    @Column(name = "price_start_date")
    private Instant priceStartDate;

    @Column(name = "price_expiry_date")
    private Instant priceExpiryDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_customizable")
    private Boolean isCustomizable;

    public static Transportation createTransportation(TransportationRequest request, Long packageId) {
        Transportation transportation = new Transportation();
        transportation.setModeMasterId(request.getModeId());
        transportation.setPriceStartDate(Instant.now());
        transportation.setPriceExpiryDate(Utils.getEndOfTime(Instant.now()));
        transportation.setPrice(request.getPrice());
        transportation.setIsCustomizable(request.getIsCustomizable());
        transportation.setPackageId(packageId);

        return transportation;
    }

}
