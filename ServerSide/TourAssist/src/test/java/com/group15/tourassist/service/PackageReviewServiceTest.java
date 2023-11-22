package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageReview;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PackageReviewTest {

    @InjectMocks
    private PackageReviewServiceService reviewService;

    @Test
    void testCalculateAveragePackageRatings() {
        // Arrange
        List<PackageReview> packageReviewList = Arrays.asList(
                createPackageReview(4),
                createPackageReview(5),
                createPackageReview(3)
        );

        // Act
        Double result = reviewService.calculateAveragePackageRatings(packageReviewList);

        // Assert
        assertEquals(4.0, result); // Adjust this based on your expected result

        // Verify interactions with mocks (if any)
        // (There are no mocks in this example, so verification is not needed)
    }

    @Test
    void testCalculateAveragePackageRatingsEmptyList() {
        // Arrange
        List<PackageReview> packageReviewList = Collections.emptyList();

        // Act
        Double result = reviewService.calculateAveragePackageRatings(packageReviewList);

        // Assert
        assertEquals(0.0, result); // The result should be 0.0 for an empty list

        // Verify interactions with mocks (if any)
        // (There are no mocks in this example, so verification is not needed)
    }

    private PackageReview createPackageReview(Integer ratings) {
        PackageReview packageReview = new PackageReview();
        packageReview.setRatings(ratings);
        // Set other properties if needed
        return packageReview;
    }
}
