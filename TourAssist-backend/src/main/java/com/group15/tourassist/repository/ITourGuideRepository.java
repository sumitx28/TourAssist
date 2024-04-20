package com.group15.tourassist.repository;

import com.group15.tourassist.entity.TourGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ITourGuideRepository extends JpaRepository<TourGuide, Long> {

    @Query("SELECT tg FROM TourGuide tg WHERE tg.packageId = :packageId")
    TourGuide getTourGuideByPackageId(@Param("packageId") Long packageId);
}
