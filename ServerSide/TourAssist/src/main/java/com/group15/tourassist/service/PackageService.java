package com.group15.tourassist.service;

import com.group15.tourassist.core.utils.Utils;
import com.group15.tourassist.dto.*;
import com.group15.tourassist.entity.*;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.entityToDto.*;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.*;
import com.group15.tourassist.response.PackageDetailResponse;
import com.group15.tourassist.web.controller.PackageController;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageService implements IPackageService {
    Logger log = LoggerFactory.getLogger(PackageController.class);
    private final IStayRepository stayRepository;
    private final ITourGuideRepository tourGuideRepository;
    private final ITransportationRepository transportationRepository;
    private final IPackageMediaRepository packageMediaRepository;
    private final IAgentRepository agentRepository;
    private final IDestinationMasterRepository destinationMasterRepository;
    private final IPackageRepository packageRepository;
    private final IActivityRepository activityRepository;
    private final IPackageMediaService packageMediaService;
    private final ActivityEntityToDto activityEntityToDto;
    private final TransportationEntityToDto transportationEntityToDto;
    private final DestinationMasterEntityToDto destinationMasterEntityToDto;
    private final SourceMasterEntityToDto sourceMasterEntityToDto;
    private final AgentEntityToDto agentEntityToDto;
    private final StayEntityToDto stayEntityToDto;

    // Transaction method to save all or nothing.
    @Override
    public Long createNewPackage(PackageCreateRequest request) {
        Package newPackage = createPackage(request);

        createStay(request.getStayRequest(), newPackage.getId());
        createTourGuide(request.getTourGuideRequest(), newPackage.getId());
        createTransportation(request.getTransportationRequest(), newPackage.getId());
        createActivities(request.getActivities(), newPackage.getId());
        createPackageMedia(request.getPackageMediaRequests(), newPackage.getId());

        return newPackage.getId();
    }

    @Override
    public PackageDetailResponse getPackageDetailsById(Long id) {

        // DestinationMasterDTO sourceMasterDto= new DestinationMasterDTO();
        // DestinationMasterDTO destinationMasterDto= new DestinationMasterDTO();
        var packageD= packageRepository.findById(id);
//        sourceMasterDto.setId(destinationMasterRepository.findById(packageD.get().getSourceId()).get().getId());
//        sourceMasterDto.setCity(destinationMasterRepository.findById(packageD.get().getSourceId()).get().getCity());
//        sourceMasterDto.setCountry(destinationMasterRepository.findById(packageD.get().getSourceId()).get().getCountry());
//        destinationMasterDto.setId(destinationMasterRepository.findById(packageD.get().getDestinationId()).get().getId());
//        destinationMasterDto.setCity(destinationMasterRepository.findById(packageD.get().getDestinationId()).get().getCity());
//        destinationMasterDto.setCountry(destinationMasterRepository.findById(packageD.get().getDestinationId()).get().getCountry());
        List<Activity> listActivity = activityRepository.getActivityDetailsByPackageId(id);
        List<ActivityDTO> listActivityDto = new ArrayList<>();;
        for (Activity activity:listActivity
        ) {
            listActivityDto.add(activityEntityToDto.activityEntityToDto(activity));
        }
        TransportationDTO transportationDto = transportationEntityToDto.transportationEntityToDto(transportationRepository.getTransportationDetailsByPackageId(id));
        SourceMasterDTO sourceMasterDto1= sourceMasterEntityToDto.sourceMasterEntityToDto(destinationMasterRepository.findById(packageD.get().getSourceId()).orElseThrow());
        DestinationMasterDTO destinationMasterDto1= destinationMasterEntityToDto.destinationMasterEntityToDto(destinationMasterRepository.findById(packageD.get().getDestinationId()).orElseThrow());
        AgentDetailsDTO agentDetailsDTO= agentEntityToDto.agentEntityToDto(agentRepository.findById(packageD.get().getAgentId()).get());
        StayDto stayDto= stayEntityToDto.stayEntityToDto(stayRepository.getStayDetailsByPackageId(id));
        PackageDetailResponse details= new PackageDetailResponse();
        details.setMediaPath(packageMediaRepository.findByPackageId(id));
        details .setAgentDetails(agentDetailsDTO);
        details.setSourceDetails(sourceMasterDto1);
        details.setDestinationDetails(destinationMasterDto1);
        details.setTripStartDate(packageD.get().getTripStartDate());
        details.setTripEndDate(packageD.get().getTripEndDate());
        details.setActivity(listActivityDto);
        details.setTransportationDetails(transportationDto);
        details.setStay(stayDto);
        details.setTourGuide(tourGuideRepository.getTourGuideByPackageId(id));
        details.setIsCustomizable(packageD.get().getIsCustomizable());
        return details;
    }


    /**
     * @param packageMediaRequests All the images for a package.
     * @param packageId package_id : primary key of package table.
     */
    private void createPackageMedia(List<PackageMediaRequest> packageMediaRequests, Long packageId) {
        packageMediaService.saveAllPackageMedia(packageMediaRequests, packageId);
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

