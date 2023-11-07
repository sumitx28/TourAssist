package com.group15.tourassist.repository;

import com.group15.tourassist.entity.PackageReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface IPackageReviewRepository extends JpaRepository<PackageReview, Long> {

    @Query("SELECT pr FROM PackageReview pr WHERE pr.packageId = :packageId")
    List<PackageReview> getPackageReviewByPackageId(@Param("packageId") Long packageId);
}
