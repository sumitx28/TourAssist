package com.group15.tourassist.service.impl;

import com.group15.tourassist.request.UpdateUserProfileRequest;

public interface IUserProfileService {

    void updateUserProfileEmail(UpdateUserProfileRequest updateUserProfileRequest);

    /**
     * updates the contact mobile number of the customer upon success auth request
     *
     * @param updateUserProfileRequest
     */
    void updateUserProfileMobile(UpdateUserProfileRequest updateUserProfileRequest);

}
