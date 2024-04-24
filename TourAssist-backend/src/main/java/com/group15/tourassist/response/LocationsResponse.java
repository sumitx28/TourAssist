package com.group15.tourassist.response;

import com.group15.tourassist.entity.DestinationMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationsResponse extends ApiResponse{
    List<DestinationMaster> locations;
}
