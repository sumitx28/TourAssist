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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final TourGuideEntityToDto tourGuideEntityToDto;

    /**
     * @param request request to create a new package in DB
     * @param images List of package images
     * @return ID of package created
     * @throws IOException exception from Cloudinary service if any.
     */
    @Override
    public Long createNewPackage(PackageCreateRequest request, List<MultipartFile> images) throws IOException {
        Package newPackage = createPackage(request);

        createStay(request.getStayRequest(), newPackage.getId());
        createTourGuide(request.getTourGuideRequest(), newPackage.getId());
        createTransportation(request.getTransportationRequest(), newPackage.getId());
        createActivities(request.getActivities(), newPackage.getId());
        createPackageMedia(images, newPackage.getId());

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
        details.setPackageName(packageD.get().getPackageName());
        details.setMediaPath(packageMediaRepository.findByPackageId(id));
        details .setAgentDetails(agentDetailsDTO);
        details.setSourceDetails(sourceMasterDto1);
        details.setDestinationDetails(destinationMasterDto1);
        details.setTripStartDate(packageD.get().getTripStartDate());
        details.setTripEndDate(packageD.get().getTripEndDate());
        details.setActivity(listActivityDto);
        details.setTransportationDetails(transportationDto);
        details.setStay(stayDto);
        details.setTourGuide(tourGuideEntityToDto.tourGuideEntityToDto(tourGuideRepository.getTourGuideByPackageId(id)));
        details.setIsCustomizable(packageD.get().getIsCustomizable());
        return details;
    }


    /**
     * @param images All the images for a package.
     * @param packageId package_id : primary key of package table.
     */
    private void createPackageMedia(List<MultipartFile> images, Long packageId) throws IOException {
        packageMediaService.saveAllPackageMedia(images, packageId);
    }

    // Create a Stay entity from request.
    private Package createPackage(PackageCreateRequest request) {
        Package newPackage = Package.createPackageFromRequest(request);
        return packageRepository.save(newPackage);
    }


    // Create a Stay entity from request.
    private void createStay(StayRequest request, Long packageId) {
        Stay stay = Stay.createStayFromRequest(request, packageId);
        stayRepository.save(stay);
    }

    // Create a tour guide entity from request.
    private void createTourGuide(TourGuideRequest request, Long packageId) {
        TourGuide tourGuide = TourGuide.createTourGuide(request, packageId);
        tourGuideRepository.save(tourGuide);
    }

    // Create a transportation entity from request.
    private void createTransportation(TransportationRequest request, Long packageId) {
        Transportation transportation = Transportation.createTransportation(request, packageId);
        transportationRepository.save(transportation);
    }

    // Create activities from request.
    private void createActivities(List<ActivityRequest> activityRequests, Long packageId) {
        List<Activity> activities = Activity.getActivities(activityRequests, packageId);
        activityRepository.saveAll(activities);
    }
}

