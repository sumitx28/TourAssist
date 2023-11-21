package com.group15.tourassist.entity;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.request.ActivityRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "activity")
public class Activity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "package_id")
    private Long packageId;

    @Column(name = "activity_master_id")
    private Long activityMasterId;

    @Column(name = "activity_date")
    private Instant activityDate;

    @Column(name = "price_start_date")
    private Instant priceStartDate;

    @Column(name = "price_expiry_date")
    private Instant priceExpiryDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_customizable")
    private Boolean isCustomizable;

    public static List<Activity> getActivities(List<ActivityRequest> activityRequests, Long packageId) {
        List<Activity> activities = new ArrayList<>();
        for (ActivityRequest activityRequest : activityRequests) {
            Activity activity = new Activity();
            activity.setActivityMasterId(activityRequest.getActivityMasterId());
            activity.setActivityDate(activityRequest.getActivityDate());
            activity.setIsCustomizable(activityRequest.getIsCustomizable());
            activity.setPriceStartDate(Instant.now());
            activity.setPriceExpiryDate(Utils.getEndOfTime(Instant.now()));
            activity.setPrice(activityRequest.getPrice());
            activity.setPackageId(packageId);

            activities.add(activity);
        }
        return activities;
    }

}
