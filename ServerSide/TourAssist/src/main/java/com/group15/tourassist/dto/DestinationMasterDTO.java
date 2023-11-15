package com.group15.tourassist.dto;

import com.group15.tourassist.entity.GuideMaster;
import com.group15.tourassist.entity.ResortMaster;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DestinationMasterDTO {

    private Long id;

    private String city;

    private String country;

    private List<ResortMaster> resorts;

    // private List<GuideMaster> guides;

}

