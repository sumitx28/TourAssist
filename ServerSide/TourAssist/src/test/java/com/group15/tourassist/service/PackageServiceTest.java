package com.group15.tourassist.service;

import com.group15.tourassist.entity.*;
import com.group15.tourassist.entity.Package;
import com.group15.tourassist.repository.*;
import com.group15.tourassist.request.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @Mock
    private IPackageRepository packageRepository;

    @Mock
    private IStayRepository stayRepository;

    @Mock
    private ITourGuideRepository tourGuideRepository;

    @Mock
    private ITransportationRepository transportationRepository;

    @Mock
    private IActivityRepository activityRepository;

    @Mock
    private PackageMediaService packageMediaService;

    private Package travelPackage;

    private PackageCreateRequest packageCreateRequest;

    private ActivityRequest activityRequest;

    private List<ActivityRequest> activityRequestList;

    private StayRequest stayRequest;

    private TourGuideRequest guideRequest;

    private TransportationRequest transportationRequest;

    private Stay stay;

    private TourGuide tourGuide;

    private Transportation transportation;

    private List<Activity> activities;

    private List<MultipartFile> images;

    @BeforeEach
    public void setup() {

        activityRequest = ActivityRequest.builder()
                .activityMasterId(1000L)
                .activityDate(Instant.now())
                .price(75.0)
                .isCustomizable(true)
                .build();

        activityRequestList = Collections.singletonList(activityRequest);

        stayRequest = StayRequest.builder()
                .resortId(1200L)
                .suiteId(1500L)
                .price(200.0)
                .isCustomizable(true)
                .build();

        guideRequest = TourGuideRequest.builder()
                .guideId(1800L)
                .price(100.0)
                .isCustomizable(true)
                .build();

        transportationRequest = TransportationRequest.builder()
                .modeId(2000L)
                .price(50.0)
                .isCustomizable(true)
                .build();

         travelPackage = Package.builder()
                .id(1L)
                .packageName("Adventure Package")
                .agentId(700L)
                .tripStartDate(Instant.now())
                .tripEndDate(Instant.now())
                .sourceId(800L)
                .destinationId(900L)
                .packageCreatedDate(Instant.now())
                .isCustomizable(true)
                .build();

        packageCreateRequest = PackageCreateRequest.builder()
                .activities(activityRequestList)
                .packageName("Test package")
                .sourceId(1L)
                .destinationId(10L)
                .stayRequest(stayRequest)
                .tourGuideRequest(guideRequest)
                .transportationRequest(transportationRequest)
                .agentId(5L)
                .isCustomizable(Boolean.TRUE)
                .tripEndDate(Instant.now())
                .tripStartDate(Instant.now())
                .build();


        travelPackage = Package.createPackageFromRequest(packageCreateRequest);
        travelPackage.setId(1L);
        stay = Stay.createStayFromRequest(stayRequest, 1L);
        tourGuide = TourGuide.createTourGuide(guideRequest, 1L);
        transportation = Transportation.createTransportation(transportationRequest, 1L);
        activities = Activity.getActivities(Collections.singletonList(activityRequest), 1L);
        images = new ArrayList<>();
    }


    @Test
    void testCreateNewPackage() throws IOException {
        // Arrange
        when(packageRepository.save(any())).thenReturn(travelPackage);
        when(stayRepository.save(any())).thenReturn(stay);
        when(tourGuideRepository.save(any())).thenReturn(tourGuide);
        when(transportationRepository.save(any())).thenReturn(transportation);
        when(activityRepository.saveAll(any())).thenReturn(activities);

        // Act
        Long packageId = packageService.createNewPackage(packageCreateRequest, new ArrayList<>());

        // Assert
        assertEquals(1L, packageId);
    }
}