package com.group15.tourassist.response;

import lombok.Data;

/**
 * response class for update user profile
 * @author snehitroda
 */
@Data
public class UpdateUserProfileWebResponse {
    private String status;
    private String message;
    private boolean isEmailUpdated;
    private boolean isMobileUpdated;

}
