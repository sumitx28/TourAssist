package com.group15.tourassist.dto;

import com.group15.tourassist.core.enums.BookingStatus;
import com.group15.tourassist.entity.ResortMaster;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsDTO {

    private Long packageId;
    private String packageName;
    private Long customerId;
    private Instant bookingDate;
    private Double totalPrice;
    private BookingStatus bookingStatus;
    private List<ActivityDTO> activityDTOS;
    private List<GuideDTO> guideDTOS;
    private ResortMaster resortDetails;
    private List<TransportationDTO> transportationDTOS;

}
