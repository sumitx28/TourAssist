package com.group15.tourassist.service;

import com.group15.tourassist.entity.Activity;
import com.group15.tourassist.entity.Stay;
import com.group15.tourassist.entity.TourGuide;
import com.group15.tourassist.entity.Transportation;
import com.group15.tourassist.repository.IActivityRepository;
import com.group15.tourassist.repository.IStayRepository;
import com.group15.tourassist.repository.ITourGuideRepository;
import com.group15.tourassist.repository.ITransportationRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TotalPackagePriceServiceTest {

    @Mock
    private ITransportationRepository transportationRepository;

    @Mock
    private IStayRepository stayRepository;

    @Mock
    private IActivityRepository activityRepository;

    @Mock
    private ITourGuideRepository tourGuideRepository;

    @InjectMocks
    private TotalPackagePriceCalculatorService priceCalculatorService;

    @Test
    public void testGetTotalPackagePrice() {
        // Arrange
        Long packageId = 1L;

        Transportation transportation = new Transportation();
        transportation.setPrice(100.0);
        when(transportationRepository.getTransportationDetailsByPackageId(packageId)).thenReturn(transportation);

        Stay stay = new Stay();
        stay.setPrice(200.0);
        when(stayRepository.getStayDetailsByPackageId(packageId)).thenReturn(stay);

        List<Activity> activityList = Arrays.asList(
                createActivity(0.0),
                createActivity(75.0)
        );
        when(activityRepository.getActivityDetailsByPackageId(packageId)).thenReturn(activityList);

        TourGuide tourGuide = new TourGuide();
        tourGuide.setPrice(120.0);
        when(tourGuideRepository.getTourGuideByPackageId(packageId)).thenReturn(tourGuide);

        // Act
        Double result = priceCalculatorService.getTotalPackagePrice(packageId);

        // Assert
        assertEquals(100.0 + 200.0 + 75.0 + 120.0, result); // Adjust this based on your expected result

        // Verify interactions with mocks
        verify(transportationRepository, times(1)).getTransportationDetailsByPackageId(packageId);
        verify(stayRepository, times(1)).getStayDetailsByPackageId(packageId);
        verify(activityRepository, times(1)).getActivityDetailsByPackageId(packageId);
        verify(tourGuideRepository, times(1)).getTourGuideByPackageId(packageId);
    }

    private Activity createActivity(Double price) {
        Activity activity = new Activity();
        activity.setPrice(price);
        return activity;
    }
}