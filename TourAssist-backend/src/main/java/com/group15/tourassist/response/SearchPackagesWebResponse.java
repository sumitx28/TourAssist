package com.group15.tourassist.response;

import com.group15.tourassist.dto.SearchTravelPackagesDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchPackagesWebResponse {

    private List<SearchTravelPackagesDTO> TravelPackages;

}
