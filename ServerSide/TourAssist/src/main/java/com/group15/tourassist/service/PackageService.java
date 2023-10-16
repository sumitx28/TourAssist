package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.entity.*;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class PackageService implements IPackageService {

    @Autowired
    private IStayRepository stayRepository;

    @Autowired
    private ITourGuideRepository tourGuideRepository;

    @Autowired
    private ITransportationRepository transportationRepository;

    @Autowired
    private IPackageRepository packageRepository;

    @Autowired
    IActivityRepository activityRepository;

    // Transaction method to save all or nothing.
    @Override
    @Transactional
    public Long createNewPackage(PackageCreateRequest request) {
        Package newPackage = createPackage(request);

        createStay(request.getStayRequest(), newPackage.getId());
        createTourGuide(request.getTourGuideRequest(), newPackage.getId());
        createTransportation(request.getTransportationRequest(), newPackage.getId());
        createActivities(request.getActivities(), newPackage.getId());

        return newPackage.getId();
    }

    // Create a Stay entity from request.
    private Package createPackage(PackageCreateRequest request) {
        Package newPackage = Package.builder()
                .sourceId(request.getSourceId())
                .destinationId(request.getDestinationId())
                .agentId(request.getAgentId())
                .packageName(request.getPackageName())
                .packageCreatedDate(Instant.now())
                .tripStartDate(request.getTripStartDate())
                .tripEndDate(request.getTripEndDate())
                .isCustomizable(request.getIsCustomizable())
                .build();

        return packageRepository.save(newPackage);
    }


    // Create a Stay entity from request.
    private void createStay(StayRequest request, Long packageId) {
        Stay stay = Stay.builder()
                .priceStartDate(Instant.now())
                .priceExpiryDate(Utils.getEndOfTime(Instant.now()))
                .price(request.getPrice())
                .resortMasterId(request.getResortId())
                .suiteMasterId(request.getSuiteId())
                .isCustomizable(request.getIsCustomizable())
                .packageId(packageId)
                .build();

        stayRepository.save(stay);
    }

    // Create a tour guide entity from request.
    private void createTourGuide(TourGuideRequest request, Long packageId) {
        TourGuide tourGuide = TourGuide.builder()
                .guideMasterId(request.getGuideId())
                .priceStartDate(Instant.now())
                .priceExpiryDate(Utils.getEndOfTime(Instant.now()))
                .price(request.getPrice())
                .isCustomizable(request.getIsCustomizable())
                .packageId(packageId)
                .build();

        tourGuideRepository.save(tourGuide);
    }

    // Create a transportation entity from request.
    private void createTransportation(TransportationRequest request, Long packageId) {
        Transportation transportation = Transportation.builder()
                .modeMasterId(request.getModeId())
                .priceStartDate(Instant.now())
                .priceExpiryDate(Utils.getEndOfTime(Instant.now()))
                .price(request.getPrice())
                .isCustomizable(request.getIsCustomizable())
                .packageId(packageId)
                .build();

        transportationRepository.save(transportation);
    }

    // Create activities from request.
    private void createActivities(List<ActivityRequest> activityRequests, Long packageId) {

        for (ActivityRequest activityRequest : activityRequests) {
            Activity activity = Activity.builder()
                    .activityMasterId(activityRequest.getActivityMasterId())
                    .activityDate(activityRequest.getActivityDate())
                    .isCustomizable(activityRequest.getIsCustomizable())
                    .priceStartDate(Instant.now())
                    .priceExpiryDate(Utils.getEndOfTime(Instant.now()))
                    .price(activityRequest.getPrice())
                    .packageId(packageId)
                    .build();

            activityRepository.save(activity);
        }
    }
}
