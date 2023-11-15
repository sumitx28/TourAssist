package com.group15.tourassist.dto;

import com.group15.tourassist.entity.DestinationMaster;
import jakarta.persistence.*;
import lombok.*;

import java.util.Optional;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResortMasterDTO {

    private String resortName;

    private Optional<DestinationMasterDTO> destinationMasterdto;
}
