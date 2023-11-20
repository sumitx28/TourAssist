package com.group15.tourassist.service;

import com.group15.tourassist.repository.IAppUserRepository;
import com.group15.tourassist.repository.ICustomerRepository;
import com.group15.tourassist.request.UpdateUserProfileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * contains the SRP methods for updating the user profile
 */
@Service
public class UserProfileService implements IUserProfileService {
    Logger log = LoggerFactory.getLogger(UserProfileService.class);

    private IAppUserRepository appUserRepository;

    private ICustomerRepository customerRepository;

    @Autowired
    public UserProfileService(IAppUserRepository appUserRepository, ICustomerRepository customerRepository) {
        this.appUserRepository = appUserRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * updates the email of the appUser upon authenticated success request
     *
     * @param updateUserProfileRequest
     */
    @Override
    public void updateUserProfileEmail(UpdateUserProfileRequest updateUserProfileRequest) {
        log.info("updateUserProfileRequest: {}", updateUserProfileRequest);
        appUserRepository.updateAppUserEmailById(updateUserProfileRequest.getAppUserId(), updateUserProfileRequest.getEmail());
        log.info("email updated successfully for appUser: {}", updateUserProfileRequest.getAppUserId());
    }

    /**
     * updates the contact mobile number of the customer upon success auth request
     *
     * @param updateUserProfileRequest
     */
    @Override
    public void updateUserProfileMobile(UpdateUserProfileRequest updateUserProfileRequest) {
        log.info("updateUserProfileRequest: {}", updateUserProfileRequest);
        int tupleUpdated = customerRepository.updateCustomerMobile(updateUserProfileRequest.getAppUserId(), updateUserProfileRequest.getMobile());
        if (tupleUpdated > 0) {
            log.info("mobile number updated successfully for appUserId: {}", updateUserProfileRequest.getAppUserId());
        } else {
            log.info("failed to update mobile for appUserId :{}", updateUserProfileRequest.getAppUserId());
        }
    }
}
