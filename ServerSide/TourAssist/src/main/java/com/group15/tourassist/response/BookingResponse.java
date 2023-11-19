package com.group15.tourassist.response;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.dto.CustomerDTO;
import com.group15.tourassist.dto.PackageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private Long id;
    private PackageDTO packageD;
    private CustomerDTO customer;
    private AgentDetailsDTO agent;
    private Instant bookingDate;
    private Double totalPrice;
    private BookingStatus bookingStatus;
}
