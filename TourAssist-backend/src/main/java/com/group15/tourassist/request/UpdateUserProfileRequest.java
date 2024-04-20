package com.group15.tourassist.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * request class used for updating the customer profile info
 *
 * @author snehitroda
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserProfileRequest {

    private Long appUserId;
    private String email;
    private String mobile; // contact number of the user
}
