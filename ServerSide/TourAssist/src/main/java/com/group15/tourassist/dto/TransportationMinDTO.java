package com.group15.tourassist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Minimum DTO created for showing transportation info in booking details
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportationMinDTO {

    private Long transportationId;
    private String transportationName;
}
