package com.group15.tourassist.response;

import com.group15.tourassist.entity.TravelModeMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TravelModesResponse extends ApiResponse{
    List<TravelModeMaster> travelModes;
}
