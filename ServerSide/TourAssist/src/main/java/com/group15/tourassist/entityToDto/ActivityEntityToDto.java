package com.group15.tourassist.entityToDto;

import com.group15.tourassist.dto.ActivityDTO;
import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.repository.IActivityMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityEntityToDto {

    @Autowired
    private IActivityMasterRepository activityMasterRepository;

    public ActivityDTO activityEntityToDto(Activity activity){
        ActivityDTO activitydto= new ActivityDTO();
        activitydto.setPackageId(activity.getPackageId());
        activitydto.setActivityMaster(activityMasterRepository.findById(activity.getActivityMasterId()));
        activitydto.setActivityDate(activity.getActivityDate());
        //activitydto.setPriceStartDate(activity.getPriceStartDate());
        //activitydto.setPriceExpiryDate(activity.getPriceExpiryDate());
        activitydto.setPrice(activity.getPrice());
        activitydto.setIsCustomizable(activity.getIsCustomizable());

        return activitydto;
    }
}
