package com.group15.tourassist.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.Instant;


/**
 * request class for customer package search
 *
 * @author snehitroda
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSearchPackageRequest {

    @NonNull
    private String sourceCity;
    @NonNull
    private String destinationCity;
    private Instant packageStartDate;
    private Instant packageEndDate;
    private Integer numberOfGuest;
}
