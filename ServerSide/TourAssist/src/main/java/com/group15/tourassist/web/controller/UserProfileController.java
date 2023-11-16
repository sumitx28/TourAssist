package com.group15.tourassist.web.controller;

import com.group15.tourassist.request.UpdateUserProfileRequest;
import com.group15.tourassist.response.UpdateUserProfileWebResponse;
import com.group15.tourassist.service.IUserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(
        origins = {
                "*",
        },
        methods = {
                RequestMethod.OPTIONS,
                RequestMethod.GET,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.POST
        })
@RestController
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final IUserProfileService userProfileService;
    Logger log = LoggerFactory.getLogger(UserProfileController.class);

    @Autowired
    public UserProfileController(IUserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/update-profile")
    public ResponseEntity<UpdateUserProfileWebResponse> updateProfile(@RequestBody UpdateUserProfileRequest updateUserProfileRequest) {
        log.info("** Updating user profile information");
        UpdateUserProfileWebResponse updateUserProfileWebResponse = new UpdateUserProfileWebResponse();
        if (updateUserProfileRequest.getEmail() != null) {
            userProfileService.updateUserProfileEmail(updateUserProfileRequest);
            updateUserProfileWebResponse.setEmailUpdated(true);
        }
        if (updateUserProfileRequest.getMobile() != null) {
            userProfileService.updateUserProfileMobile(updateUserProfileRequest);
            updateUserProfileWebResponse.setMobileUpdated(true);
        }
        updateUserProfileWebResponse.setStatus("success");
        updateUserProfileWebResponse.setMessage("User profile updated successfully");
        return ResponseEntity.ok(updateUserProfileWebResponse);
    }
}