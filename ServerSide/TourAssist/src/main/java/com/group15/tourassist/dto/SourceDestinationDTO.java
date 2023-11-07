package com.group15.tourassist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SourceDestinationDTO {

    private Long sourceCityID;
    private Long destinationCityID;


}
