package com.group15.tourassist.response;

import com.group15.tourassist.entity.GuideMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuidesResponse extends ApiResponse{
    List<GuideMaster> guides;
}
