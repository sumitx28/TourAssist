package com.group15.tourassist.service;

import com.group15.tourassist.entity.PackageReview;
import com.group15.tourassist.service.impl.IPackageReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageReviewService implements IPackageReviewService {

    /**
     * calculates the average package ratings accross all the package reviews for that package id
     *
     * @param packageReviewList
     * @return
     */
    @Override
    public Double calculateAveragePackageRatings(List<PackageReview> packageReviewList) {
        Double averageRatings = 0.0;

        if (packageReviewList.isEmpty()) {
            return averageRatings;
        }

        Double packageRatingsSum = 0.0;
        int packageCount = 0;
        for (PackageReview packageReview : packageReviewList) {
            packageRatingsSum += packageReview.getRatings();
            packageCount++;
        }

        if (packageCount > 0) {
            averageRatings = packageRatingsSum / packageCount;
        }
        return averageRatings;
    }
}
