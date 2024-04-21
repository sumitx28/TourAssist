package com.group15.tourassist.service.impl;

import com.group15.tourassist.entity.PackageReview;

import java.util.List;

public interface IPackageReviewService {

    Double calculateAveragePackageRatings(List<PackageReview> packageReviewList);
}
