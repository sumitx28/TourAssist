package com.group15.tourassist.response;

import com.group15.tourassist.entity.SuiteMaster;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuitesResponse extends ApiResponse{
    List<SuiteMaster> suites;
}
