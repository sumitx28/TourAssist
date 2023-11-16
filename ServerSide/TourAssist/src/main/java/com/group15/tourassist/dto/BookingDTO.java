package com.group15.tourassist.dto;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.entity.Agent;
import com.group15.tourassist.entity.Customer;
import com.group15.tourassist.entity.Package;

import lombok.*;

import java.time.Instant;
import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingDTO {
    private Long id;
    private Optional<Package> packageD;
    private Optional<Customer> customer;
    private Optional<Agent> agent;
    private Instant bookingDate;
    private Double totalPrice;
    private BookingStatus bookingStatus;

}
