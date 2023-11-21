package com.group15.tourassist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentDetailsDTO {

    private Long agentId;
    private String companyName;
    private String mobile;

}
