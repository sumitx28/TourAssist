package com.group15.tourassist.response;

import com.group15.tourassist.dto.AgentDetailsDTO;
import com.group15.tourassist.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailsBookedByAgentIDResponse {
    private AgentDetailsDTO agent;
    private Customer customer;
}
